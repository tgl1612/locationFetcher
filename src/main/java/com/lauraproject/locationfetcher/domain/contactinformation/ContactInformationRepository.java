package com.lauraproject.locationfetcher.domain.contactinformation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation, String> {

    public List<ContactInformation> findAllByLocationId(String locationId);
}
