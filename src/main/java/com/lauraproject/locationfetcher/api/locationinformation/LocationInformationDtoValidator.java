package com.lauraproject.locationfetcher.api.locationinformation;

import com.lauraproject.locationfetcher.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LocationInformationDtoValidator {

    public void isValid(LocationInformationDto dto){
        List<String> errors = new ArrayList<>();
        if(dto == null){
            throw new ValidationException("Invalid request - Location information must be provided");
        }
        if(dto.getLocationType() == null || dto.getLocationType().isEmpty()){
            errors.add("Location must have location type");
        }
        if(dto.getAddressLine1() == null || dto.getAddressLine1().isEmpty()){
            errors.add("Location must have address line 1");
        }
        if(dto.getCity() == null || dto.getCity().isEmpty()){
            errors.add("Location must have city");
        }

        if(!errors.isEmpty()){
            throw new ValidationException("Invalid request - " + errors);
        }
    }
}
