package com.lauraproject.locationfetcher.domain.locationinformation;

import com.lauraproject.locationfetcher.exception.NotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LocationInformationService {

    private LocationInformationRepository repository;

    @Transactional(readOnly = true)
    public List<LocationInformation> findAllLocations(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public LocationInformation findLocationByLocationId(String locationId){
        return repository.findById(locationId)
            .orElseThrow(() -> new NotFoundException("No location found matching id: " + locationId));
    }

    @Transactional
    public LocationInformation saveLocationInformation(LocationInformation locationInformation){
        return repository.save(locationInformation);
    }

    @Transactional
    public void deleteLocationInformation(String locationId){
        repository.deleteById(locationId);
    }
}
