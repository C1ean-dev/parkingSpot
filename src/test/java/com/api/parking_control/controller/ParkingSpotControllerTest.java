package com.api.parking_control.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api.parking.controller.ParkingSpotController;
import com.api.parking.models.ParkingSpotModel;
import com.api.parking.service.ParkingSpotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingSpotController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveParkingSpot_whenApartmentAndBlockAlreadyExists() throws Exception {
        // Arrange
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        parkingSpotModel.setParkingSpotNumber("123");
        parkingSpotModel.setLicensePlateCar("ABC1234");
        parkingSpotModel.setBrandCar("Toyota");
        parkingSpotModel.setModelCar("Corolla");
        parkingSpotModel.setColorCar("Red");
        parkingSpotModel.setResponsibleName("John Doe");
        parkingSpotModel.setApartment("101");
        parkingSpotModel.setBlock("A");

        when(parkingSpotService.invalidateParkingSpot(parkingSpotModel)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(post("/parking-spot/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(parkingSpotModel)))
                .andExpect(status().is(HttpStatus.CONFLICT.value()))
                .andExpect(content().string("Conflict: Parking Spot already registered for this Apartment/block."));
    }
}