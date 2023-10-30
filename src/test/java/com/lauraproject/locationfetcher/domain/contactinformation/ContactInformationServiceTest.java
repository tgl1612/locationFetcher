package com.lauraproject.locationfetcher.domain.contactinformation;

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
public class ContactInformationServiceTest {

    private static final String ID = "ID";
    private static final String LOCATION_ID = "LOCATION_ID";

    @Mock
    private ContactInformationRepository repository;

    @InjectMocks
    private ContactInformationService service;

    @Test
    public void shouldReturnContactInformationWhenIdMatches(){

        ContactInformation entity = createContactInformationEntity();
        given(repository.findById(ID)).willReturn(Optional.of(entity));

        ContactInformation returnedEntity = service.findContactInformationById(ID);
        assertThat(returnedEntity).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenIdNotMatching(){
        given(repository.findById(ID)).willReturn((Optional.empty()));
        assertThatThrownBy(() -> service.findContactInformationById(ID)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void shouldReturnAllContactInformationForLocation(){
        ContactInformation entity = createContactInformationEntity();
        given(repository.findAllByLocationId(LOCATION_ID)).willReturn(List.of(entity));

        List<ContactInformation> contactList = service.findAllContactInformationByLocationId(LOCATION_ID);

        assertThat(contactList).usingRecursiveComparison().isEqualTo(List.of(entity));
    }

    @Test
    public void shouldSaveContactInformation(){
        ContactInformation entity = createContactInformationEntity();
        given(repository.save(entity)).willReturn(entity);
        ContactInformation savedEntity = service.saveContactInformation(entity);
        assertThat(savedEntity).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    public void shouldDeleteContactInformationById(){
        service.deleteContactInformation(ID);
        then(repository).should().deleteById(ID);
    }

    private ContactInformation createContactInformationEntity(){
        ContactInformation entity = new ContactInformation();
        entity.setId(ID);
        entity.setContactType(ContactType.LOCATION);
        entity.setEmail("email");
        entity.setFirstName("firstName");
        entity.setLastName("lastName");
        entity.setPhoneNumber("phoneNumber");
        entity.setLocationId(LOCATION_ID);
        return entity;
    }

}
