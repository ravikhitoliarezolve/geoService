package com.resolve.geofenceService.ControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resolve.geoService.controller.GeoController;
import com.resolve.geoService.entity.GeoEntity;
import com.resolve.geoService.service.GeoService;
import com.resolve.geofenceService.AppConstantTest;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

	  @Autowired
	    MockMvc mockMvc;

	    @InjectMocks
	    GeoController geoController;

	    @Mock
	    GeoService geoService;
	    ObjectMapper mapper = new ObjectMapper();
	    
	    @BeforeEach
	    public void setup() {
	        mockMvc = MockMvcBuilders.standaloneSetup(geoController)
	                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
	                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
	    }

	    @Test
	    void createGeofence() throws Exception {
	        GeoEntity geoModel = new GeoEntity();
	        geoModel.setId(121L);
	        geoModel.setRadius(10000.0);
	        geoModel.setLatitude(10.0);
	        geoModel.setLongitude(11.0);
	        String json = mapper.writeValueAsString(geoModel);
	        RequestBuilder mvCRequest = MockMvcRequestBuilders.post(AppConstantTest.CONTROLLER_BASE_URL + "/createGeo")
	                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
	        mockMvc.perform(mvCRequest).andExpect(status().isOk());
	    }

	    @Test
	    void getGeofence() throws Exception {
	        RequestBuilder mvCRequest = MockMvcRequestBuilders.get(AppConstantTest.CONTROLLER_BASE_URL + "/getGeos")
	                .accept(MediaType.APPLICATION_JSON);
	        mockMvc.perform(mvCRequest).andExpect(status().isOk());
	    }

	    @Test
	    void deleteGeofence() throws Exception {
	        RequestBuilder mvCRequest = MockMvcRequestBuilders.delete(AppConstantTest.CONTROLLER_BASE_URL + "/deleteGeo/1")
	                .accept(MediaType.APPLICATION_JSON);
	        mockMvc.perform(mvCRequest).andExpect(status().isOk());
	    }
	 
	    @Test
	    void updateGeofence_bad_request() throws Exception {
	        RequestBuilder mvCRequest = MockMvcRequestBuilders.put(AppConstantTest.CONTROLLER_BASE_URL + "/updateGeo")
	                .accept(MediaType.APPLICATION_JSON);
	        mockMvc.perform(mvCRequest).andExpect(status().isBadRequest());
	    }

	    @Test
	    void updateGeo() throws Exception {
	        GeoEntity geoModel = new GeoEntity();
	        geoModel.setId(121L);
	        geoModel.setRadius(10001.0);
	        geoModel.setLatitude(11.0);
	        geoModel.setLongitude(12.0);
	        String json = mapper.writeValueAsString(geoModel);
	        RequestBuilder mvCRequest = MockMvcRequestBuilders.put(AppConstantTest.CONTROLLER_BASE_URL + "/updateGeo")
	                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
	        mockMvc.perform(mvCRequest).andExpect(status().isOk());
	    }

	}