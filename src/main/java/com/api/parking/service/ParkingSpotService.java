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

    final
    ParkingSpotRepository repository;
    public ParkingSpotService(ParkingSpotRepository repository) {
        this.repository = repository;
    }
    public boolean existsByLicensePlateCar(String licensePlateCar){
        return repository.existsByLicensePlateCar(licensePlateCar);
    }
    public boolean existsByParkingSpotNumber(String parkingSpotNumber){
        return repository.existsByParkingSpotNumber(parkingSpotNumber);
    }
    public boolean existsByApartmentAndBlock(String apartment, String block){
        return repository.existsByApartmentAndBlock(apartment, block);
    }
    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
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
    public void delete(ParkingSpotModel parkingSpotModel){
        repository.delete(parkingSpotModel);
    }

    @Transactional
    @CachePut(value = "Spots", key = "#id")
    public ParkingSpotModel update(ParkingSpotModel parkingSpotModel){
        return repository.save(parkingSpotModel);
    }
    
}
