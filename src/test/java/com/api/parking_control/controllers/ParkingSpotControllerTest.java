package com.api.parking_control.controllers;

import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.service.ParkingSpotService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringJUnitConfig
class ParkingSpotControllerTest {

    

    // Teste para o método saveParkingSpot
    @ParameterizedTest
    @MethodSource("provideSaveParkingSpotTestCases")
    void testSaveParkingSpot(String licensePlate, String parkingSpotNumber, String apartment, String block, boolean conflict) {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setLicensePlateCar(licensePlate);
        parkingSpotDto.setParkingSpotNumber(parkingSpotNumber);
        parkingSpotDto.setApartment(apartment);
        parkingSpotDto.setBlock(block);

        ResponseEntity<Object> response = parkingSpotController.saveParkingSpot(parkingSpotDto);

        if (conflict) {
            assertEquals(409, response.getStatusCode().value());
            verify(parkingSpotService, never()).save(any());
        } else {
            System.out.println(response.toString());
            assertEquals(201, response.getStatusCode().value());
            verify(parkingSpotService, times(1)).save(any());
        }
    }

    private static Stream<Object[]> provideSaveParkingSpotTestCases() {
        return Stream.of(
                new Object[]{"ABC1234", "1", "Apt1", "BlockA", false},
                new Object[]{"ABC1234", "1", "Apt1", "BlockA", true},
                new Object[]{"JKL1122", "4", "Apt4", "BlockD", false} // Sucesso
        );
    }

    // Teste para o método getOneParkingSpot
    @ParameterizedTest
    @MethodSource("provideGetOneParkingSpotTestCases")
    void testGetOneParkingSpot(UUID id, boolean spotExists) {
        if (spotExists) {
            ParkingSpotModel model = new ParkingSpotModel();
            model.setId(id);
            when(parkingSpotService.findById(id)).thenReturn(Optional.of(model));
        } else {
            when(parkingSpotService.findById(id)).thenReturn(Optional.empty());
        }

        ResponseEntity<Object> response = parkingSpotController.getOneParkingSpot(id);

        if (spotExists) {
            assertEquals(200, response.getStatusCode().value());
        } else {
            assertEquals(404, response.getStatusCode().value());
            assertEquals("Parking Spot not found.", response.getBody());
        }
    }

    private static Stream<Object[]> provideGetOneParkingSpotTestCases() {
        return Stream.of(
                new Object[]{UUID.randomUUID(), true},
                new Object[]{UUID.randomUUID(), false}
        );
    }

    // Teste para o método deleteOneParkingSpot
    @ParameterizedTest
    @MethodSource("provideDeleteOneParkingSpotTestCases")
    void testDeleteOneParkingSpot(UUID id, boolean spotExists) {
        if (spotExists) {
            ParkingSpotModel model = new ParkingSpotModel();
            model.setId(id);
            when(parkingSpotService.findById(id)).thenReturn(Optional.of(model));
        } else {
            when(parkingSpotService.findById(id)).thenReturn(Optional.empty());
        }

        ResponseEntity<Object> response = parkingSpotController.deleteOneParkingSpot(id);

        if (spotExists) {
            verify(parkingSpotService, times(1)).delete(any(ParkingSpotModel.class));
            assertEquals(200, response.getStatusCode().value());
            assertEquals("Parking Spot Deleted successfully", response.getBody());
        } else {
            assertEquals(404, response.getStatusCode().value());
            assertEquals("Parking Spot not found", response.getBody());
        }
    }

    private static Stream<Object[]> provideDeleteOneParkingSpotTestCases() {
        return Stream.of(
                new Object[]{UUID.randomUUID(), true},
                new Object[]{UUID.randomUUID(), false}
        );
    }
}
