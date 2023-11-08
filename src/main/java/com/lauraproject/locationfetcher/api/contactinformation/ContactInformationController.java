package com.lauraproject.locationfetcher.api.contactinformation;

import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformation;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformationService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("contact-information")
public class ContactInformationController {

    private ContactInformationService service;
    private ContactInformationDtoValidator validator;

    @GetMapping("/location/{locationId}")
    public List<ContactInformationDto> getAllContactInformationByLocationId(
        @PathVariable(name = "locationId") String locationId
    ) {
        log.info("Returning all contact information for location with id: " + locationId);
        List<ContactInformation> entityList = service.findAllContactInformationByLocationId(locationId);
        List<ContactInformationDto> dtoList = new ArrayList<>(entityList.size());

        entityList.forEach(entity -> {
            dtoList.add(ContactInformationDto.fromModel(entity));
        });
        return dtoList;
    }

    @PostMapping("/location/{locationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactInformationDto createContactInformationForLocation(
        @PathVariable String locationId,
        @RequestBody ContactInformationDto contactInformationDto
    ) {
        log.info("Creating new contact information for location with id: " + locationId);
        validator.isValid(contactInformationDto);
        ContactInformation contactInformation = ContactInformation.fromDto(contactInformationDto);
        contactInformation.setLocationId(locationId);
        ContactInformation savedEntity = service.saveContactInformation(contactInformation);
        log.info("New contact information created with id: {} for location with id: {}", savedEntity.getId(), locationId);
        return ContactInformationDto.fromModel(savedEntity);
    }

    @PutMapping("/{contactId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactInformationDto updateContactInformation(
        @PathVariable(name = "contactId") String contactId,
        @RequestBody ContactInformationDto contactInformationDto
    ) {
        log.info("Updating contactInformation with id: " + contactId);
        ContactInformation existingEntity = service.findContactInformationById(contactId);
        validator.isValid(contactInformationDto);

        ContactInformation entityToUpdate = ContactInformation.fromDto(contactInformationDto);
        entityToUpdate.setId(contactId);
        entityToUpdate.setLocationId(existingEntity.getLocationId());

        ContactInformation updatedEntity = service.saveContactInformation(entityToUpdate);
        return ContactInformationDto.fromModel(updatedEntity);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContactInformation(
        @PathVariable(name = "contactId") String contactId
    ) {
        log.info("Deleting contactInformation with id: " + contactId);
        service.findContactInformationById(contactId);
        service.deleteContactInformation(contactId);
    }
}
