package com.lauraproject.locationfetcher.api.contactinformation;

import com.lauraproject.locationfetcher.domain.contactInformation.ContactInformation;
import com.lauraproject.locationfetcher.domain.contactInformation.ContactInformationService;
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
@RequestMapping("contact-information")
public class ContactInformationController {

    private ContactInformationService service;

    @GetMapping("/{locationId}")
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
}
