package com.lauraproject.locationfetcher.api.locationInformation;

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

    @GetMapping()
    public List<LocationInformationDto> getAllLocations() {
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
        return LocationInformationDto.fromModel(service.findLocationByLocationId(locationId));
    }

    @PostMapping()
    public LocationInformationDto createLocationInformation(
        @RequestBody LocationInformationDto locationInformationDto
    ) {
        //todo add logs
        //todo add validation
        //todo testing
        //todo readme
        //todo revise sets, lists, tree sets, what they are and when to use etc.
        //todo revise TDD, BDD, DDD best practices
        //todo write a kafka example to show experience with Kafka in github.
        LocationInformation locationInformation = LocationInformation.fromDto(locationInformationDto);
        LocationInformation savedEntity = service.saveLocationInformation(locationInformation);
        return LocationInformationDto.fromModel(savedEntity);
    }

    @PutMapping("/{locationId}")
    public LocationInformationDto updateLocationInformation(
        @PathVariable(name = "locationId") String locationId,
        @RequestBody LocationInformationDto locationInformationDto
    ) {
        service.findLocationByLocationId(locationId);
        //todo validation on dto
        LocationInformation entityToUpdate = LocationInformation.fromDto(locationInformationDto);
        entityToUpdate.setLocationId(locationId);
        LocationInformation updatedEntity = service.saveLocationInformation(entityToUpdate);
        return LocationInformationDto.fromModel(updatedEntity);

    }

    @DeleteMapping("/{locationId}")
    public void deleteLocationInformation(
        @PathVariable(name = "locationId") String locationId
    ) {
        service.findLocationByLocationId(locationId);
        service.deleteLocationInformation(locationId);
    }
}
