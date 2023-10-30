package com.lauraproject.locationfetcher.api.locationinformation;

import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformation;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformationService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("location-information")
public class LocationInformationController {

    private LocationInformationService service;
    private LocationInformationDtoValidator validator;

    @GetMapping()
    public List<LocationInformationDto> getAllLocations() {
        log.info("Returning all locations");
        List<LocationInformation> entityList = service.findAllLocations();
        List<LocationInformationDto> dtoList = new ArrayList<>(entityList.size());

        entityList.forEach(entity -> {
            dtoList.add(LocationInformationDto.fromModel(entity));
        });
        return dtoList;
    }

    @GetMapping("/{locationId}")
    public LocationInformationDto getLocationByLocationId(
        @PathVariable(name = "locationId") String locationId
    ) {
        log.info("Returning location by id: " + locationId);
        return LocationInformationDto.fromModel(service.findLocationByLocationId(locationId));
    }

    @PostMapping()
    public LocationInformationDto createLocationInformation(
        @RequestBody LocationInformationDto locationInformationDto
    ) {

        log.info("Creating new location");
        validator.isValid(locationInformationDto);
        LocationInformation locationInformation = LocationInformation.fromDto(locationInformationDto);
        LocationInformation savedEntity = service.saveLocationInformation(locationInformation);
        log.info("New location created with id: " + savedEntity.getLocationId());
        return LocationInformationDto.fromModel(savedEntity);
    }

    @PutMapping("/{locationId}")
    public LocationInformationDto updateLocationInformation(
        @PathVariable(name = "locationId") String locationId,
        @RequestBody LocationInformationDto locationInformationDto
    ) {
        log.info("Updating location with id: " + locationId);
        service.findLocationByLocationId(locationId);
        validator.isValid(locationInformationDto);
        LocationInformation entityToUpdate = LocationInformation.fromDto(locationInformationDto);
        entityToUpdate.setLocationId(locationId);
        LocationInformation updatedEntity = service.saveLocationInformation(entityToUpdate);
        return LocationInformationDto.fromModel(updatedEntity);

    }

    @DeleteMapping("/{locationId}")
    public void deleteLocationInformation(
        @PathVariable(name = "locationId") String locationId
    ) {
        log.info("Deleting location with id: " + locationId);
        service.findLocationByLocationId(locationId);
        service.deleteLocationInformation(locationId);
    }
}
