package com.resolve.geoService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class swaggerConfig {
	
			public class SwaggerUIConfiguration {

		    @Bean
		    public Docket api(){
		                return new Docket(DocumentationType.SWAGGER_2)
		                .select()
		                .apis(RequestHandlerSelectors.basePackage("com.resolve.geoService.controller"))
//		                .paths(regex("/api.*"))
		                 .apis(RequestHandlerSelectors.any())
		                 .paths(PathSelectors.any())
		                 .build();
		    }


		}

	}


