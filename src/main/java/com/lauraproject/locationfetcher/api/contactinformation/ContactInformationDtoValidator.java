package com.lauraproject.locationfetcher.api.contactinformation;

import com.lauraproject.locationfetcher.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ContactInformationDtoValidator {

    public void isValid(ContactInformationDto dto){
        List<String> errors = new ArrayList<>();

        if(dto == null){
            throw new ValidationException("Invalid request - Contact information must be provided");
        }
        if ((dto.getEmail() == null || dto.getEmail().isEmpty()) && (dto.getPhoneNumber() == null
            || dto.getPhoneNumber().isEmpty())) {
            errors.add("Contact information must contain email or phone number.");
        }
        if(dto.getContactType() == null){
            errors.add("Contact information must contain contact type.");
        }

        if(!errors.isEmpty()){
            throw new ValidationException("Invalid request - " + errors);

        }
    }
}
