package com.lauraproject.locationfetcher.domain.locationinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationInformationRepository extends JpaRepository<LocationInformation, String> {
}
