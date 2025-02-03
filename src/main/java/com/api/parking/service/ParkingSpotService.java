package com.api.parking.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.parking.models.ParkingSpotModel;
import com.api.parking.repositories.ParkingSpotRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository repository;

    public ParkingSpotService(ParkingSpotRepository repository) {
        this.repository = repository;
    }
    public boolean invalidateParkingSpot(ParkingSpotModel parkingSpotModel) {
        return repository.existsByLicensePlateCar(parkingSpotModel.getLicensePlateCar()) ||
                repository.existsByParkingSpotNumber(parkingSpotModel.getParkingSpotNumber()) ||
                repository.existsByApartmentAndBlock(parkingSpotModel.getApartment(), parkingSpotModel.getBlock());
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        if (invalidateParkingSpot(parkingSpotModel)) {
            throw new IllegalArgumentException("Parking spot already in use");
        }
        return repository.save(parkingSpotModel);
    }
    
    @Transactional(readOnly = true)
    public Page<ParkingSpotModel> findAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    
    @Cacheable(value = "Spots")
    public Optional<ParkingSpotModel> findById(UUID id){
        return repository.findById(id);
    }
    
    @Transactional
    @CacheEvict(value = "Spots", key = "#id")
    public void delete(UUID id){
        repository.deleteById(id);
    }

    @Transactional
    @CachePut(value = "Spots", key = "#id")
    public ParkingSpotModel update(ParkingSpotModel parkingSpotModel){
        if (invalidateParkingSpot(parkingSpotModel)) {
            throw new IllegalArgumentException("Parking spot already in use");
        }
        if (!repository.existsById(parkingSpotModel.getId())) {
            throw new IllegalArgumentException("Parking spot not found");
        }
        return repository.save(parkingSpotModel);
    }
    
}
