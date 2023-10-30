package com.lauraproject.locationfetcher.domain.contactinformation;

import com.lauraproject.locationfetcher.exception.NotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class ContactInformationService {

    private ContactInformationRepository repository;

    @Transactional(readOnly = true)
    public ContactInformation findContactInformationById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("No contact information found matching id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ContactInformation> findAllContactInformationByLocationId(String locationId) {
        return repository.findAllByLocationId(locationId);
    }

    @Transactional
    public ContactInformation saveContactInformation(ContactInformation contactInformation) {
        return repository.save(contactInformation);
    }

    @Transactional
    public void deleteContactInformation(String id) {
        repository.deleteById(id);
    }

}
