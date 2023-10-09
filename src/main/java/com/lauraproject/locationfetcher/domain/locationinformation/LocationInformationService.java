package com.lauraproject.locationfetcher.domain.locationinformation;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class LocationInformationService {

    private LocationInformationRepository repository;

    public List<LocationInformation> findAllLocations(){
        return repository.findAll();
    }

    public LocationInformation findLocationByLocationId(String locationId){
        return repository.findById(locationId).orElseThrow();
    }
}
