package com.lauraproject.locationfetcher.api.contactinformation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformation;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformationRepository;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformationService;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactType;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformation;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformationRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
public class ContactInformationApiComponentTest {

    private static final String ID = "ID";
    private static final String EMAIL = "EMAIL";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String BASE_PATH = "/contact-information/";
    private static final String LOCATION_PATH_EXTENSION = "location/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ContactInformationService service;
    @Autowired
    private ContactInformationDtoValidator validator;
    @Autowired
    private ContactInformationRepository contactRepository;
    @Autowired
    private LocationInformationRepository locationRepository;

    @BeforeEach
    public void beforeEach() {
        cleanDB();
    }

    @AfterEach
    public void afterEach() {
        cleanDB();
    }

    private void cleanDB() {
        contactRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    public void testGetAllContactInformationByLocationId() throws Exception {
        String locationId = createLocationInformationEntity().getLocationId();
        ContactInformation savedEntity = createContactInformationEntity(locationId);
        ContactInformationDto expectedDto = createContactInformationDto();
        expectedDto.setId(savedEntity.getId());

        String result = mapper.writeValueAsString(List.of(expectedDto));

        mockMvc.perform(get(BASE_PATH + LOCATION_PATH_EXTENSION + locationId))
            .andExpect(status().isOk())
            .andExpect(content().json(result));
    }

    @Test
    public void testCreateContactInformationForLocation() throws Exception {
        String locationId = createLocationInformationEntity().getLocationId();
        ContactInformationDto dto = createContactInformationDto();

        String request = mapper.writeValueAsString(dto);

        final MvcResult result = mockMvc.perform(post(BASE_PATH + LOCATION_PATH_EXTENSION + locationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isCreated())
            .andReturn();

        final ContactInformationDto returnedDto = mapper.readValue(result.getResponse().getContentAsByteArray(),
            ContactInformationDto.class);

        assertThat(returnedDto.getId()).isNotEmpty();
        assertThat(returnedDto.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(returnedDto.getContactType()).isEqualTo(dto.getContactType());
    }

    @Test
    public void testUpdateContactInformation() throws Exception {
        String locationId = createLocationInformationEntity().getLocationId();
        ContactInformation savedEntity = createContactInformationEntity(locationId);
        ContactInformationDto dto = createContactInformationDto();
        dto.setContactType(ContactType.LOCATION_MANAGER);

        String request = mapper.writeValueAsString(dto);

        final MvcResult result = mockMvc.perform(put(BASE_PATH + savedEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isCreated())
            .andReturn();

        final ContactInformationDto returnedDto = mapper.readValue(result.getResponse().getContentAsByteArray(),
            ContactInformationDto.class);

        assertThat(returnedDto.getId()).isEqualTo(savedEntity.getId());
        assertThat(returnedDto.getContactType()).isEqualTo(ContactType.LOCATION_MANAGER);

    }

    @Test
    public void testDeleteContactInformation() throws Exception {
        String locationId = createLocationInformationEntity().getLocationId();
        ContactInformation savedEntity = createContactInformationEntity(locationId);

        mockMvc.perform(delete(BASE_PATH + savedEntity.getId()))
            .andExpect(status().isOk());

        assertThat(contactRepository.findById(savedEntity.getLocationId())).isEmpty();
    }

    private ContactInformationDto createContactInformationDto() {
        ContactInformationDto dto = new ContactInformationDto();
        dto.setContactType(ContactType.LOCATION);
        dto.setEmail(EMAIL);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        return dto;
    }

    private ContactInformation createContactInformationEntity(String locationId) {
        ContactInformation entity = new ContactInformation();
        entity.setContactType(ContactType.LOCATION);
        entity.setEmail(EMAIL);
        entity.setFirstName(FIRST_NAME);
        entity.setLastName(LAST_NAME);
        entity.setPhoneNumber(PHONE_NUMBER);
        entity.setLocationId(locationId);
        return service.saveContactInformation(entity);
    }

    private LocationInformation createLocationInformationEntity() {
        LocationInformation entity = new LocationInformation();
        entity.setLocationType("LOCATION_TYPE");
        entity.setAddressLine1("ADDRESS_LINE_1");
        entity.setCity("CITY");
        return locationRepository.save(entity);
    }
}
