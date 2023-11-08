package com.lauraproject.locationfetcher.api.locationinformation;

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
import com.lauraproject.locationfetcher.domain.contactinformation.ContactType;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformation;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformationRepository;
import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformationService;
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
public class LocationInformationApiComponentTest {

    private static final String LOCATION_TYPE = "LOCATION_TYPE";
    private static final String ADDRESS_LINE_1 = "ADDRESS_LINE_1";
    private static final String ADDRESS_LINE_2 = "ADDRESS_LINE_2";
    private static final String CITY = "CITY";
    private static final String POST_CODE = "POST_CODE";
    private static final String COUNTRY = "COUNTRY";
    private static final String PRICE = "PRICE";
    private static final String KEYWORDS = "KEYWORDS";
    private static final String BASE_PATH = "/location-information/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private LocationInformationService service;
    @Autowired
    private LocationInformationDtoValidator validator;
    @Autowired
    private LocationInformationRepository locationRepository;
    @Autowired
    private ContactInformationRepository contactRepository;

    @BeforeEach
    public void beforeEach() {cleanDB();}

    @AfterEach
    public void afterEach() {
        cleanDB();
    }

    private void cleanDB() {
        locationRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Test
    public void testGetAllLocations() throws Exception {
        LocationInformation savedEntity = createLocationInformationEntity();

        String result = mapper.writeValueAsString(List.of(savedEntity));

        mockMvc.perform(get(BASE_PATH))
            .andExpect(status().isOk())
            .andExpect(content().json(result));
    }

    @Test
    public void testGetLocationByLocationId() throws Exception {
        LocationInformation savedEntity = createLocationInformationEntity();

        String result = mapper.writeValueAsString(savedEntity);

        mockMvc.perform(get(BASE_PATH + savedEntity.getLocationId()))
            .andExpect(status().isOk())
            .andExpect(content().json(result));
    }

    @Test
    public void testCreateLocation() throws Exception {

        LocationInformationDto dto = createLocationInformationDto();

        String request = mapper.writeValueAsString(dto);

        final MvcResult result = mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isCreated())
            .andReturn();

        final LocationInformationDto returnedDto = mapper.readValue(result.getResponse().getContentAsByteArray(),
            LocationInformationDto.class);

        assertThat(returnedDto.getLocationId()).isNotEmpty();
        assertThat(returnedDto.getLocationType()).isEqualTo(dto.getLocationType());
        assertThat(returnedDto.getAddressLine1()).isEqualTo(dto.getAddressLine1());
    }

    @Test
    public void testUpdateLocation() throws Exception {

        LocationInformation savedEntity = createLocationInformationEntity();
        LocationInformationDto dto = createLocationInformationDto();
        dto.setLocationType("inside");

        String request = mapper.writeValueAsString(dto);

        final MvcResult result = mockMvc.perform(put(BASE_PATH + savedEntity.getLocationId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isCreated())
            .andReturn();

        final LocationInformationDto returnedDto = mapper.readValue(result.getResponse().getContentAsByteArray(),
            LocationInformationDto.class);

        assertThat(returnedDto.getLocationId()).isEqualTo(savedEntity.getLocationId());
        assertThat(returnedDto.getLocationType()).isEqualTo("inside");
    }

    @Test
    public void testDeleteLocation() throws Exception {
        LocationInformation savedEntity = createLocationInformationEntity();
        createContactInformationEntity(savedEntity.getLocationId());

        mockMvc.perform(delete(BASE_PATH + savedEntity.getLocationId()))
            .andExpect(status().isOk());

        assertThat(locationRepository.findById(savedEntity.getLocationId())).isEmpty();
        assertThat(contactRepository.findAllByLocationId(savedEntity.getLocationId())).isEmpty();
    }

    private LocationInformationDto createLocationInformationDto() {
        LocationInformationDto dto = new LocationInformationDto();
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

    private LocationInformation createLocationInformationEntity() {
        LocationInformation entity = new LocationInformation();
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
        return service.saveLocationInformation(entity);
    }

    private ContactInformation createContactInformationEntity(String locationId) {
        ContactInformation entity = new ContactInformation();
        entity.setContactType(ContactType.LOCATION);
        entity.setEmail("EMAIL");
        entity.setFirstName("FIRST_NAME");
        entity.setLastName("LAST_NAME");
        entity.setPhoneNumber("PHONE_NUMBER");
        entity.setLocationId(locationId);
        return contactRepository.save(entity);
    }
}
