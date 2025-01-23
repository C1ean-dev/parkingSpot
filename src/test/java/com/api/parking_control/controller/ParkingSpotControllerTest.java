package com.api.parking_control.controller;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.api.parking.controller.ParkingSpotController;
import com.api.parking.dtos.ParkingSpotDto;
import com.api.parking.service.ParkingSpotService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@ExtendWith(MockitoExtension.class)
class ParkingSpotControllerTest {

    @Mock
    private ParkingSpotService parkingSpotService;

    @Mock
    private ParkingSpotDto parkingSpotDto;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @Test
    @DisplayName("Não deve permitir criar vaga com apartamento e bloco duplicados")
    void testSaveParkingSpot_whenApartmentAndBlockAlreadyExists() {

        // Simula que já existe uma vaga com o mesmo apartamento e bloco
        when(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock()))
                .thenReturn(true); // Simulando a existência da vaga duplicada
        //ACT
        HttpStatusCode response = parkingSpotController.saveParkingSpot(parkingSpotDto).getStatusCode();
        // Assert
        assertEquals(HttpStatus.CONFLICT, response);
    }
}
