package com.resolve.geoService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resolve.geoService.constant.ApplicationConstants;
import com.resolve.geoService.dto.ErrorResponseDto;
import com.resolve.geoService.dto.ResponseDto;
import com.resolve.geoService.dto.SuccessResponseDto;
import com.resolve.geoService.entity.GeoEntity;
import com.resolve.geoService.repository.GeoRepository;

@Service
public class GeoService {
    @Autowired
    GeoRepository geoRepository;


    /*
    This methods creates new geofence and returns unique id in the response
     */
    public ResponseDto createGeo(GeoEntity geoEntity) {
        return new SuccessResponseDto(geoRepository.save(geoEntity));
    }

    /*
    This method returns all available geofence in the system.
     */
    public ResponseDto getGeo() {
        List<GeoEntity> geoFences = geoRepository.findAll();
        return new SuccessResponseDto(geoFences);
    }

    /*
       This method delete geofence in the system bases on unique geoId.
        */
    public ResponseDto deleteGeo(Long geoId) {
        if (geoRepository.findById(geoId).isPresent()) {
            geoRepository.deleteById(geoId);
            return new SuccessResponseDto(ApplicationConstants.HTTP_RESPONSE_SUCCESS_CODE);
        }

        return new ErrorResponseDto(ApplicationConstants.HTTP_RESPONSE_ERROR_CODE_NOT_FOUND, ApplicationConstants.HTTP_RESPONSE_ERROR_CODE_NOT_FOUND_MSG);
    }

    /*
     This method updates geofence data in the system bases on unique geoId.
      */
    public ResponseDto updateGeo(GeoEntity geoEntity) {
        return geoRepository.findById(geoEntity.getId()).isPresent()
                ? new SuccessResponseDto(geoRepository.save(geoEntity)) : new ErrorResponseDto(ApplicationConstants.HTTP_RESPONSE_ERROR_CODE_NOT_FOUND, ApplicationConstants.HTTP_RESPONSE_ERROR_CODE_NOT_FOUND_MSG);
    }

}
