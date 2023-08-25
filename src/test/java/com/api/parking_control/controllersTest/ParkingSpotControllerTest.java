package com.api.parking_control.controllersTest;

import com.api.parking_control.controllers.ParkingSpotController;
import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingSpotControllerTest {

    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveParkingSpot_ValidParkingSpot_ReturnsCreatedResponse() {
        // Arrange
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        // Set up the parkingSpotDto
        Mockito.when(parkingSpotService.existsByLicensePlateCar(any())).thenReturn(false);
        Mockito.when(parkingSpotService.existsByParkingSpotNumber(any())).thenReturn(false);
        Mockito.when(parkingSpotService.existsByApartmentAndBlock(any(), any())).thenReturn(false);
        Mockito.when(parkingSpotService.save(any())).thenReturn(new ParkingSpotModel());

        // Act
        ResponseEntity<Object> response = parkingSpotController.saveParkingSpot(parkingSpotDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(parkingSpotService, times(1)).save(any());
    }

    @Test
    void saveParkingSpot_ConflictingLicensePlateCar_ReturnsConflictResponse() {
        // Arrange
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        // Set up the parkingSpotDto

        when(parkingSpotService.existsByLicensePlateCar(any())).thenReturn(true);

        // Act
        ResponseEntity<Object> response = parkingSpotController.saveParkingSpot(parkingSpotDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Conflict: License Plate Car is already in use!", response.getBody());
        verify(parkingSpotService, never()).save(any());
    }

    @Test
    void getOneParkingSpot_ExistingId_ReturnsOkResponse() {
        // Arrange
        UUID id = UUID.randomUUID();
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        // Set up the parkingSpotModel

        when(parkingSpotService.findById(id)).thenReturn(Optional.of(parkingSpotModel));

        // Act
        ResponseEntity<Object> response = parkingSpotController.getOneParkingSpot(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(parkingSpotModel, response.getBody());
    }

    @Test
    void getOneParkingSpot_NonExistingId_ReturnsNotFoundResponse() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(parkingSpotService.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Object> response = parkingSpotController.getOneParkingSpot(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Parking Spot not found.", response.getBody());
    }

}