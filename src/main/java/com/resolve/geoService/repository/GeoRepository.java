package com.resolve.geoService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resolve.geoService.entity.GeoEntity;

@Repository
public interface GeoRepository extends JpaRepository<GeoEntity, Long> {
}
