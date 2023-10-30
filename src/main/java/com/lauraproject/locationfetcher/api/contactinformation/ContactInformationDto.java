package com.lauraproject.locationfetcher.api.contactinformation;

import com.lauraproject.locationfetcher.domain.contactinformation.ContactInformation;
import com.lauraproject.locationfetcher.domain.contactinformation.ContactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactInformationDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private ContactType contactType;


    public static ContactInformationDto fromModel(ContactInformation entity){
        ContactInformationDto dto = new ContactInformationDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setContactType(entity.getContactType());
        return dto;
    }
}
