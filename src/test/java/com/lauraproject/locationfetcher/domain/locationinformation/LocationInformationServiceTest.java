package com.lauraproject.locationfetcher.domain.locationinformation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.lauraproject.locationfetcher.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationInformationServiceTest {

    private static final String LOCATION_ID = "LOCATION_ID";

    @Mock
    private LocationInformationRepository repository;

    @InjectMocks
    private LocationInformationService service;

    @Test
    public void shouldReturnListOfLocations() {
        LocationInformation entity = createLocationEntity();
        given(repository.findAll()).willReturn((List.of(entity)));

        List<LocationInformation> locationList = service.findAllLocations();
        assertThat(locationList).usingRecursiveComparison().isEqualTo(List.of(entity));

    }

    @Test
    public void shouldReturnLocationWhenIdMatches() {
        LocationInformation entity = createLocationEntity();
        given(repository.findById(LOCATION_ID)).willReturn((Optional.of(entity)));

        LocationInformation returnedEntity = service.findLocationByLocationId(LOCATION_ID);
        assertThat(returnedEntity).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenIdNotMatching() {
        given(repository.findById(LOCATION_ID)).willReturn((Optional.empty()));
        assertThatThrownBy(() -> service.findLocationByLocationId(LOCATION_ID)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void shouldSaveLocationInformation() {
        LocationInformation entity = createLocationEntity();
        given(repository.save(entity)).willReturn(entity);
        LocationInformation savedEntity = service.saveLocationInformation(entity);
        assertThat(savedEntity).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    public void shouldDeleteLocationInformationById() {
        service.deleteLocationInformation(LOCATION_ID);
        then(repository).should().deleteById(LOCATION_ID);
    }

    private LocationInformation createLocationEntity() {
        LocationInformation entity = new LocationInformation();
        entity.setLocationId(LOCATION_ID);
        entity.setLocationType("outdoors");
        entity.setAddressLine1("addressLine1");
        entity.setAddressLine2("addressLine2");
        entity.setCity("Belfast");
        entity.setPostCode("12345");
        entity.setCountry("Ireland");
        entity.setPrice("24e");
        entity.setParking(true);
        entity.setPermitRequired(false);
        entity.setRamp(true);
        entity.setLift(false);
        entity.setDirect(true);
        entity.setKeywords("outdoors, nature");
        return entity;
    }
}
