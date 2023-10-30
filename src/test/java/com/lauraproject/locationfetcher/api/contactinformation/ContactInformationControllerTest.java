package com.lauraproject.locationfetcher.api.contactinformation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformation;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformationService;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ContactInformationControllerTest {

    private static final String ID = "ID";
    private static final String EMAIL = "EMAIL";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String LOCATION_ID = "LOCATION_ID";

    @Mock
    private ContactInformationService service;
    @Mock
    private ContactInformationDtoValidator validator;
    @InjectMocks
    private ContactInformationController controller;

    @Test
    public void should_returnContactInformationDtoList_for_givenLocationId() {

        List<ContactInformation> entityList = List.of(createContactInformationEntity());
        List<ContactInformationDto> expectedDtoList = List.of(createContactInformationDto());

        given(service.findAllContactInformationByLocationId(LOCATION_ID)).willReturn(entityList);

        List<ContactInformationDto> actualDtoList = controller.getAllContactInformationByLocationId(LOCATION_ID);

        assertThat(actualDtoList).usingRecursiveComparison().isEqualTo(expectedDtoList);
    }

    @Test
    public void should_createContactInformation_for_givenLocationId_and_returnDto() {

        ContactInformationDto expectedDto = createContactInformationDto();
        ContactInformation entity = createContactInformationEntity();

        given(service.saveContactInformation(any(ContactInformation.class))).willReturn(entity);

        ContactInformationDto actualDto = controller.createContactInformationForLocation(LOCATION_ID, expectedDto);

        then(service).should().saveContactInformation(any(ContactInformation.class));
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    public void should_updateContactInformation_for_givenId_and_returnDto() {

        ContactInformationDto expectedDto = createContactInformationDto();
        ContactInformationDto editedDto = createContactInformationDto();
        editedDto.setId("TEST_ID");

        ContactInformation entity = createContactInformationEntity();

        given(service.findContactInformationById(ID)).willReturn(entity);
        given(service.saveContactInformation(any(ContactInformation.class))).willReturn(entity);

        ContactInformationDto actualDto = controller.updateContactInformation(ID, editedDto);

        then(service).should().saveContactInformation(any(ContactInformation.class));
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    public void should_deleteContactInformation_for_givenId() {

        controller.deleteContactInformation(ID);

        then(service).should().findContactInformationById(ID);
        then(service).should().deleteContactInformation(ID);
    }

    private ContactInformationDto createContactInformationDto() {
        ContactInformationDto dto = new ContactInformationDto();
        dto.setId(ID);
        dto.setContactType(ContactType.LOCATION);
        dto.setEmail(EMAIL);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        return dto;
    }

    private ContactInformation createContactInformationEntity() {
        ContactInformation entity = new ContactInformation();
        entity.setId(ID);
        entity.setContactType(ContactType.LOCATION);
        entity.setEmail(EMAIL);
        entity.setFirstName(FIRST_NAME);
        entity.setLastName(LAST_NAME);
        entity.setPhoneNumber(PHONE_NUMBER);
        entity.setLocationId(LOCATION_ID);
        return entity;
    }
}
