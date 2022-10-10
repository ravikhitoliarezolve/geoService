package com.resolve.geoService.exception;

import com.resolve.geoService.constant.ApplicationConstants;
import com.resolve.geoService.dto.ErrorResponseDto;
import com.resolve.geoService.dto.ResponseDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> handle(ServiceException ex) {
        String errorCode = ex.getErrorCode();
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        HttpStatus httpStatus = ex.getHttpStatus();
        String[] tempCodeString = errorCode.split("_");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1]), errorMessage);
        log.error("Service Exception | errorCode : {} | errorMessage : {} | httpStatus : {}", errorCode, errorMessage, httpStatus);
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

    @ExceptionHandler(Throwable.class)//Exception does not cover all scenarios
    public ResponseEntity<ResponseDto<?>> handle(Exception ex) {
        log.error("Something went wrong. Exception : ", ex);
        log.error("Something went wrong. Exception.toString() : {}", ex.toString());
        String errorCode = "SAS_1001";
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        String[] tempCodeString = errorCode.split("_");

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1]), errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ApplicationConstants.HTTP_BAD_REQUEST_CODE, "Bad Request");
        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Request Validation failed");
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage()).collect(Collectors.toList());
        log.error("Validation error list : " + validationList);
        String errorCode = "1006";
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        String[] tempCodeString = errorCode.split("_");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1]), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Required headers missing : ", ex);
        String errorCode = "SAS_1005";
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        String[] tempCodeString = errorCode.split("_");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1]), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

}