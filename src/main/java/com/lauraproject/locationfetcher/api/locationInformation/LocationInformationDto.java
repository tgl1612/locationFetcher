package com.lauraproject.locationfetcher.api.locationInformation;

import com.lauraproject.locationfetcher.domain.locationinformation.LocationInformation;
import javax.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationInformationDto {

    private String locationId;
    private String locationType;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postCode;
    private String country;
    private String price;
    private boolean parking;
    private boolean permitRequired;
    private boolean ramp;
    private boolean lift;
    private boolean direct;
    private String keywords;

    public static LocationInformationDto fromModel(LocationInformation entity){
        LocationInformationDto dto = new LocationInformationDto();
        dto.setLocationId(entity.getLocationId());
        dto.setLocationType(entity.getLocationType());
        dto.setAddressLine1(entity.getAddressLine1());
        dto.setAddressLine2(entity.getAddressLine2());
        dto.setCity(entity.getCity());
        dto.setPostCode(entity.getPostCode());
        dto.setCountry(entity.getCountry());
        dto.setPrice(entity.getPrice());
        dto.setParking(entity.isParking());
        dto.setPermitRequired(entity.isPermitRequired());
        dto.setRamp(entity.isRamp());
        dto.setLift(entity.isLift());
        dto.setDirect(entity.isDirect());
        dto.setKeywords(entity.getKeywords());
        return dto;
    }



}
