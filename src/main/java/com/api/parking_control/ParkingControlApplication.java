package com.api.parking_control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParkingControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingControlApplication.class, args);
    }

    @GetMapping("/")
    public String index(){
        return """
                to use the car parking API make requests to /parking-spot.
                to add a car: POST() /parking-spot
                body JSON:
                {
                "parkingSpotNumber": "",
                "licensePlateCar": "",
                "brandCar": "",
                "modelCar": "",
                "colorCar": "",
                "responsibleName": "",
                "apartment": "",
                "block": ""
                }
                to return all vehicles /parking-spot.
                to return a specific vehicle: GET() /parking-spot/{ID}
                to delete a specific vehicle: DEL() /parking-spot/{ID}
                to update a specific vehicle: PUT() /parking-spot/{ID}
                """;
    }
}
