//nao esta pronta

package com.api.parking_control.controllersTest;

import com.api.parking_control.controllers.ParkingSpotController;
import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class ParkingSpotControllerRefactorTest {

    @Mock
    private ParkingSpotModel parkingSpotModel;
    @Mock
    private ParkingSpotService parkingSpotService;
    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
         // Criando uma instância de ParkingSpotModel com ID
         parkingSpotModel = new ParkingSpotModel();
         parkingSpotModel.setId(UUID.fromString("5d3bb51d-2fe9-41dd-9fb0-016fb6254039"));  // Definindo um ID único
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
        return Stream.of(
                Arguments.of(false, false, false, HttpStatus.CREATED),
                Arguments.of(true, false, false, HttpStatus.CONFLICT),
                Arguments.of(false, true, false, HttpStatus.CONFLICT),
                Arguments.of(false, false, true, HttpStatus.CONFLICT)
        );
    }

    //GetCarById
    @ParameterizedTest
    @MethodSource("scenariosGetById")
    @DisplayName("😱")
    void getOneParkingSpot_ExistingId_ReturnsExpectedStatus(String idString, HttpStatus expectedStatus) {
        // Convertendo a String para UUID
        UUID id = UUID.fromString(idString);

        // Mockando o comportamento do serviço
        if (expectedStatus == HttpStatus.OK) {
            // Se for OK, deve retornar um modelo de estacionamento
            when(parkingSpotService.findById(id)).thenReturn(Optional.of(parkingSpotModel));
        } else if (expectedStatus == HttpStatus.NOT_FOUND) {
            // Se for NOT_FOUND, deve retornar Optional.empty()
            when(parkingSpotService.findById(id)).thenReturn(Optional.empty());
        }

        // Executando a chamada ao controlador
        ResponseEntity<Object> response = parkingSpotController.getOneParkingSpot(id);

        // Verificando o status da resposta
        assertEquals(expectedStatus, response.getStatusCode());

        // Para fins de depuração
        System.out.println("Status da resposta: " + response.getStatusCode() + " | Esperado: " + expectedStatus);
    }
    static Stream<Arguments> scenariosGetById() {
    return Stream.of(
            Arguments.of("5d3bb51d-2fe9-41dd-9fb0-016fb6254039", HttpStatus.OK),
            Arguments.of("11111111-2222-3333-4444-555555555555", HttpStatus.NOT_FOUND)
    );
}

    //DeleteCarById
    @ParameterizedTest
    @MethodSource("scenariosDeleteById")
    void deleteOneParkingSpot_ExistingId_ReturnsOkResponse(UUID id, HttpStatus expectedStatus) {

        // Arrange (Preparar)
        if (expectedStatus == HttpStatus.OK) {
            when(parkingSpotService.findById(id)).thenReturn(Optional.of(parkingSpotModel));
        } else if (expectedStatus == HttpStatus.NOT_FOUND) {
            when(parkingSpotService.findById(id)).thenReturn(Optional.empty());
        }
        // Act (Agir)
        ResponseEntity<Object> response = parkingSpotController.deleteOneParkingSpot(id);
        // Assert (Verificar)
        System.out.println("this status from response is: " + response.getStatusCode() + " this expected is: " + expectedStatus);
        assertEquals(expectedStatus, response.getStatusCode());
    }
    static Stream<Arguments> scenariosDeleteById() {
        return Stream.of(
            Arguments.of("5d3bb51d-2fe9-41dd-9fb0-016fb6254039", HttpStatus.OK),
            Arguments.of("5d3bb51d-2fe9-41dd-9fb0-016fb6254039", HttpStatus.NOT_FOUND),
            Arguments.of("11111111-2222-3333-4444-555555555555", HttpStatus.NOT_FOUND)
        );
    }

    //DeleteCarV2
    @ParameterizedTest
    @MethodSource("scenariosDeleteByIdV2")
    void testDeleteOneParkingSpot(String idString,  HttpStatus expectedStatus){
        UUID id = UUID.fromString(idString);
        // Configuração do mock para os cenários do teste
        if (expectedStatus == HttpStatus.OK) {
            when(parkingSpotService.findById(id)).thenReturn(Optional.of(new ParkingSpotModel()));
            doNothing().when(parkingSpotService).delete(any(ParkingSpotModel.class));
        } else if (expectedStatus == HttpStatus.NOT_FOUND) {
            when(parkingSpotService.findById(id)).thenReturn(Optional.empty());
        }
        // Chamada do método a ser testado
        ResponseEntity<Object> response = parkingSpotController.deleteOneParkingSpot(id);

        // Validação do resultado
        assertEquals(expectedStatus, response.getStatusCode());
    }
    static Stream<Arguments> scenariosDeleteByIdV2() {
        return Stream.of(
            Arguments.of("5d3bb51d-2fe9-41dd-9fb0-016fb6254039", HttpStatus.OK),
            Arguments.of("11111111-2222-3333-4444-555555555555", HttpStatus.NOT_FOUND)
        );
    }
}