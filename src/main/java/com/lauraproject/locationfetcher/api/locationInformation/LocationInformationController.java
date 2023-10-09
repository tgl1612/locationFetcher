package com.lauraproject.locationfetcher.api.locationInformation;

import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformation;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformationService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("location-information")
public class LocationInformationController {

    private LocationInformationService service;

    @GetMapping()
    public List<LocationInformationDto> getAllLocations(){
        List<LocationInformation> entityList = service.findAllLocations();
        List<LocationInformationDto> dtoList = new ArrayList<>(entityList.size());

        entityList.forEach(entity ->{
            dtoList.add(LocationInformationDto.fromModel(entity));
        });
        return dtoList;
    }

    @GetMapping("/{locationId}")
    public LocationInformationDto getLocationByLocationId(
        @PathVariable(name = "locationId") String locationId
    ){
        return LocationInformationDto.fromModel(service.findLocationByLocationId(locationId));
    }
}
