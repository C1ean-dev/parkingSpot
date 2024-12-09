package com.api.parking_control.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.parking.dtos.ParkingSpotDto;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ParkingSpotDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validate_ValidParkingSpotDto_NoValidationErrors() {
        // Arrange
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setParkingSpotNumber("123");
        parkingSpotDto.setLicensePlateCar("ABC123");
        parkingSpotDto.setBrandCar("Brand");
        parkingSpotDto.setModelCar("Model");
        parkingSpotDto.setColorCar("Color");
        parkingSpotDto.setRegistrationDate(LocalDateTime.now());
        parkingSpotDto.setResponsibleName("John Doe");
        parkingSpotDto.setApartment("123");
        parkingSpotDto.setBlock("A");

        // Act
        Set<ConstraintViolation<ParkingSpotDto>> violations = validator.validate(parkingSpotDto);

        // Assert
        assertEquals(0, violations.size());
    }

    @Test
    void validate_InvalidParkingSpotDto_ValidationErrors() {
        // Arrange
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setParkingSpotNumber(null);
        parkingSpotDto.setLicensePlateCar("ABC1234567"); // Exceeds max size
        parkingSpotDto.setBrandCar(null);
        parkingSpotDto.setModelCar(null);
        parkingSpotDto.setColorCar(null);
        parkingSpotDto.setRegistrationDate(null);
        parkingSpotDto.setResponsibleName(null);
        parkingSpotDto.setApartment(null);
        parkingSpotDto.setBlock(null);

        // Act
        Set<ConstraintViolation<ParkingSpotDto>> violations = validator.validate(parkingSpotDto);

        // Assert
        assertEquals(8, violations.size());
    }

}