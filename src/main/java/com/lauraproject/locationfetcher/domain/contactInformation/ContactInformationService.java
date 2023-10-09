package com.lauraproject.locationfetcher.domain.contactInformation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ContactInformationService {

    private ContactInformationRepository repository;

    public List<ContactInformation> findAllContactInformationByLocationId(String locationId){

        return repository.findAllByLocationId(locationId);

    }
}
