package com.resolve.geoService.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resolve.geoService.dto.ResponseDto;
import com.resolve.geoService.entity.GeoEntity;
import com.resolve.geoService.service.GeoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/geo/api")
public class GeoController {

    @Autowired
    GeoService geoService;

    @PostMapping("/createGeo")
    public ResponseDto addGeoFence(@Valid @RequestBody GeoEntity request) {
        log.info("creating geofence for request {}", request);
        return geoService.createGeo(request);
    }

    @GetMapping("/getGeos")
    public ResponseDto getGeoFences() {
        log.info("finding geofence list ");
        return geoService.getGeo();
    }

    @DeleteMapping("/deleteGeo"+"/{geoId}")
    public ResponseDto deleteGeo(@PathVariable final Long geoId) {
        log.info("deleting geofence for {} ", geoId);
        return geoService.deleteGeo(geoId);
    }

    @PutMapping("/updateGeo")
    public ResponseDto updateGeo(@Valid @RequestBody GeoEntity request) {
        log.info("updating geofence for {}", request);
        return geoService.updateGeo(request);
    }

}
