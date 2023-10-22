package com.lauraproject.locationfetcher.api.contactinformation;

import com.lauraproject.locationfetcher.domain.contactInformation.ContactInformation;
import com.lauraproject.locationfetcher.domain.contactInformation.ContactInformationService;
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
@RequestMapping("contact-information")
public class ContactInformationController {

    private ContactInformationService service;

    @GetMapping("/location/{locationId}")
    public List<ContactInformationDto> getAllContactInformationByLocationId(
        @PathVariable(name = "locationId") String locationId
    ) {
        List<ContactInformation> entityList = service.findAllContactInformationByLocationId(locationId);
        List<ContactInformationDto> dtoList = new ArrayList<>(entityList.size());

        entityList.forEach(entity -> {
            dtoList.add(ContactInformationDto.fromModel(entity));
        });
        return dtoList;
    }

    @PostMapping()
    public ContactInformationDto createContactInformation(
        @RequestBody ContactInformationDto contactInformationDto
    ) {
        ContactInformation contactInformation = ContactInformation.fromDto(contactInformationDto);
        ContactInformation savedEntity = service.saveContactInformation(contactInformation);
        return ContactInformationDto.fromModel(savedEntity);
    }

    @PutMapping("/{contactId}")
    public ContactInformationDto updateContactInformation(
        @PathVariable(name = "contactId") String contactId,
        @RequestBody ContactInformationDto contactInformationDto
    ) {
        service.findContactInformationById(contactId);
        //todo validation on dto
        ContactInformation entityToUpdate = ContactInformation.fromDto(contactInformationDto);
        entityToUpdate.setId(contactId);

        ContactInformation updatedEntity = service.saveContactInformation(entityToUpdate);
        return ContactInformationDto.fromModel(updatedEntity);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContactInformation(
        @PathVariable(name = "contactId") String contactId
    ) {
        service.findContactInformationById(contactId);
        service.deleteContactInformation(contactId);
    }
}
