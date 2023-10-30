package com.lauraproject.locationfetcher.api.locationinformation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformation;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformationService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationInformationControllerTest {

    @Mock
    private LocationInformationService service;
    @Mock
    private LocationInformationDtoValidator validator;

    @InjectMocks
    private LocationInformationController controller;

    private static final String LOCATION_ID = "LOCATION_ID";
    private static final String LOCATION_TYPE = "LOCATION_TYPE";
    private static final String ADDRESS_LINE_1 = "ADDRESS_LINE_1";
    private static final String ADDRESS_LINE_2 = "ADDRESS_LINE_2";
    private static final String CITY = "CITY";
    private static final String POST_CODE = "POST_CODE";
    private static final String COUNTRY = "COUNTRY";
    private static final String PRICE = "PRICE";
    private static final String KEYWORDS = "KEYWORDS";

    @Test
    public void should_returnList_of_allLocationInformation() {

        LocationInformation entity = createLocationInformation();
        List<LocationInformationDto> expectedDtoList = List.of(createLocationInformationDto());
        given(service.findAllLocations()).willReturn(List.of(entity));

        List<LocationInformationDto> actualDtoList = controller.getAllLocations();

        assertThat(actualDtoList).usingRecursiveComparison().isEqualTo(expectedDtoList);

    }

    @Test
    public void should_returnLocationInformation_by_locationId() {

        LocationInformationDto expectedDto = createLocationInformationDto();
        LocationInformation entity = createLocationInformation();

        given(service.findLocationByLocationId(LOCATION_ID)).willReturn(entity);

        LocationInformationDto actualDto = controller.getLocationByLocationId(LOCATION_ID);

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    public void should_createLocationInformation() {

        LocationInformationDto expectedDto = createLocationInformationDto();
        LocationInformation entity = createLocationInformation();

        given(service.saveLocationInformation(any(LocationInformation.class))).willReturn(entity);

        LocationInformationDto actualDto = controller.createLocationInformation(expectedDto);

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    public void should_updateLocationInformation_for_givenLocationId() {

        LocationInformationDto expectedDto = createLocationInformationDto();
        LocationInformationDto editedDto = createLocationInformationDto();
        editedDto.setLocationId("TEST_ID");
        LocationInformation entity = createLocationInformation();

        given(service.saveLocationInformation(any(LocationInformation.class))).willReturn(entity);

        LocationInformationDto actualDto = controller.updateLocationInformation(LOCATION_ID, editedDto);

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);

    }

    @Test
    public void should_deleteLocationInformation_for_givenLocationId() {

        controller.deleteLocationInformation(LOCATION_ID);

        then(service).should().findLocationByLocationId(LOCATION_ID);
        then(service).should().deleteLocationInformation(LOCATION_ID);
    }

    private LocationInformationDto createLocationInformationDto() {
        LocationInformationDto dto = new LocationInformationDto();
        dto.setLocationId(LOCATION_ID);
        dto.setLocationType(LOCATION_TYPE);
        dto.setAddressLine1(ADDRESS_LINE_1);
        dto.setAddressLine2(ADDRESS_LINE_2);
        dto.setCity(CITY);
        dto.setPostCode(POST_CODE);
        dto.setCountry(COUNTRY);
        dto.setPrice(PRICE);
        dto.setParking(true);
        dto.setPermitRequired(true);
        dto.setRamp(true);
        dto.setLift(true);
        dto.setDirect(true);
        dto.setKeywords(KEYWORDS);
        return dto;
    }

    private LocationInformation createLocationInformation() {
        LocationInformation entity = new LocationInformation();
        entity.setLocationId(LOCATION_ID);
        entity.setLocationType(LOCATION_TYPE);
        entity.setAddressLine1(ADDRESS_LINE_1);
        entity.setAddressLine2(ADDRESS_LINE_2);
        entity.setCity(CITY);
        entity.setPostCode(POST_CODE);
        entity.setCountry(COUNTRY);
        entity.setPrice(PRICE);
        entity.setParking(true);
        entity.setPermitRequired(true);
        entity.setRamp(true);
        entity.setLift(true);
        entity.setDirect(true);
        entity.setKeywords(KEYWORDS);
        return entity;
    }

}
