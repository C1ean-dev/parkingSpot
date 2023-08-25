package com.api.parking_control.controllersTest;

import com.api.parking_control.controllers.ParkingSpotController;
import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class ParkingSpotControllerRefactorTest {

    @Mock
    private ParkingSpotService parkingSpotService;
    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //postNewCar ParameterizedTest
    @ParameterizedTest
    @MethodSource("scenariosSave")
    void test_SaveParkingSpot_Responses(boolean licencePlateCar,boolean SpotNumber,boolean ApartmentAndBlock, HttpStatus expectedStatus){
        // ARRANGE
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        Mockito.when(parkingSpotService.existsByLicensePlateCar(any())).thenReturn(licencePlateCar);
        Mockito.when(parkingSpotService.existsByParkingSpotNumber(any())).thenReturn(SpotNumber);
        Mockito.when(parkingSpotService.existsByApartmentAndBlock(any(), any())).thenReturn(ApartmentAndBlock);
        Mockito.when(parkingSpotService.save(any())).thenReturn(new ParkingSpotModel());
        //ACT
        ResponseEntity<Object> response = parkingSpotController.saveParkingSpot(parkingSpotDto);
        //ASSERT
        assertEquals(expectedStatus, response.getStatusCode());
        System.out.println("this status from response is: " + response.getStatusCode() + " this expected is: " + expectedStatus);
    }
    static Stream<Arguments> scenariosSave() {
        //to test all cases I will use a n^m matrix.
        //where n = number of possibilities = true, false = 2
        //e m = number of possible combinations = licensePlateCar, SpotNumber, ApartmentAndBlock = 3
        return Stream.of(
                Arguments.of(false, false, false, HttpStatus.CREATED),
                Arguments.of(true, false, false, HttpStatus.CONFLICT),
                Arguments.of(false, true, false, HttpStatus.CONFLICT),
                Arguments.of(false, false, true, HttpStatus.CONFLICT),
                Arguments.of(true, true, false, HttpStatus.CONFLICT),
                Arguments.of(true, false, true, HttpStatus.CONFLICT),
                Arguments.of(false, true, true, HttpStatus.CONFLICT),
                Arguments.of(true, true, true, HttpStatus.CONFLICT)

        );
    }

    //GetCarById
    @ParameterizedTest
    @MethodSource("scenariosGetById")
    void getOneParkingSpot_ExistingId_ReturnsOkResponse(UUID id, HttpStatus expectedStatus) {
        // Arrange
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        when(parkingSpotService.findById(id)).thenReturn(Optional.of(parkingSpotModel));
        // Act
        ResponseEntity<Object> response = parkingSpotController.getOneParkingSpot(id);
        // Assert
        System.out.println("this status from response is: " + response.getStatusCode() + " this expected is: " + expectedStatus);
        assertEquals(expectedStatus, response.getStatusCode());
    }
    static Stream<Arguments> scenariosGetById() {
        return Stream.of(
                Arguments.of("ead6ef40-3dee-4555-9eb4-5ec64f17cbe9", HttpStatus.OK),
                Arguments.of("5adb9c13-ba9e-4cf2-8dc5-a75c7ae709e9", HttpStatus.OK),
                Arguments.of("11111111-2222-3333-4444-555555555555", HttpStatus.NOT_FOUND)
        );
    }

    //DeleteCarById
    @ParameterizedTest
    @MethodSource("scenariosDeleteById")
    void deleteOneParkingSpot_ExistingId_ReturnsOkResponse(UUID id, HttpStatus expectedStatus) {
        // Arrange
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        when(parkingSpotService.findById(id)).thenReturn(Optional.of(parkingSpotModel));
        // Act
        ResponseEntity<Object> response = parkingSpotController.deleteOneParkingSpot(id);
        // Assert
        System.out.println("this status from response is: " + response.getStatusCode() + " " + response.getBody() +  " this expected is: " + expectedStatus);
        assertEquals(expectedStatus, response.getStatusCode());
    }
    static Stream<Arguments> scenariosDeleteById() {
        return Stream.of(
                Arguments.of("ead6ef40-3dee-4555-9eb4-5ec64f17cbe9", HttpStatus.OK),
                Arguments.of("5adb9c13-ba9e-4cf2-8dc5-a75c7ae709e9", HttpStatus.OK),
                Arguments.of("11111111-2222-3333-4444-555555555555", HttpStatus.NOT_FOUND)
        );
    }
    //Update


}