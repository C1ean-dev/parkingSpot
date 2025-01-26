package com.api.parking.models;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "ParkingSpot")
@Data
@Table(name = "ParkingSpot")
public class ParkingSpotModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false,unique = true,length = 10)
    private String parkingSpotNumber;
    @Column(nullable = false,unique = true,length = 7)
    private String licensePlateCar;
    @Column(nullable = false, length = 70)
    private String brandCar;
    @Column(nullable = false, length = 70)
    private String modelCar;
    @Column(nullable = false, length = 70)
    private String colorCar;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = true)
    private LocalDateTime updateDate;
    @Column(nullable = false, length = 130)
    private String responsibleName;
    @Column(nullable = false, length = 30)
    private String apartment;
    @Column(nullable = false, length = 30)
    private String block;

    public String toString() {
        return "ParkingSpotModel{" +
                "id=" + id +
                ", parkingSpotNumber='" + parkingSpotNumber + '\'' +
                ", licensePlateCar='" + licensePlateCar + '\'' +
                ", brandCar='" + brandCar + '\'' +
                ", modelCar='" + modelCar + '\'' +
                ", colorCar='" + colorCar + '\'' +
                ", registrationDate=" + registrationDate +
                ", responsibleName='" + responsibleName + '\'' +
                ", apartment='" + apartment + '\'' +
                ", block='" + block + '\'' +
                '}';
    }

}
