package com.api.parking_control.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api.parking.controller.ParkingSpotController;
import com.api.parking.dtos.ParkingSpotDto;
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
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setParkingSpotNumber("123");
        parkingSpotDto.setLicensePlateCar("ABC1234");
        parkingSpotDto.setBrandCar("Toyota");
        parkingSpotDto.setModelCar("Corolla");
        parkingSpotDto.setColorCar("Red");
        parkingSpotDto.setResponsibleName("John Doe");
        parkingSpotDto.setApartment("101");
        parkingSpotDto.setBlock("A");

        when(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar()))
            .thenReturn(false);
        when(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber()))
            .thenReturn(false);
        when(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock()))
            .thenReturn(true);
        // Act & Assert
        mockMvc.perform(post("/parking-spot/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(parkingSpotDto)))
                .andExpect(status().is(HttpStatus.CONFLICT.value()))
                .andExpect(content().string("Conflict: Parking Spot already registered for this Apartment/block."));
    }
}