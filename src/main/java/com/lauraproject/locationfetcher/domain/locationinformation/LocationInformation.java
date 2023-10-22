package com.lauraproject.locationfetcher.domain.locationinformation;

import com.lauraproject.locationfetcher.api.locationInformation.LocationInformationDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_location_information")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationInformation {

    @Id
    @Column(name = "location_id", nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String locationId;
    @Column(name = "location_type")
    private String locationType;
    @Column(name = "address_line_1")
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Column(name = "city")
    private String city;
    @Column(name = "post_code")
    private String postCode;
    @Column(name = "country")
    private String country;
    @Column(name = "price")
    private String price;
    @Column(name = "parking")
    private boolean parking;
    @Column(name = "permit_required")
    private boolean permitRequired;
    @Column(name = "ramp")
    private boolean ramp;
    @Column(name = "lift")
    private boolean lift;
    @Column(name = "direct")
    private boolean direct;
    @Column(name = "keywords")
    private String keywords;


    public static LocationInformation fromDto(LocationInformationDto dto){

        LocationInformation entity = new LocationInformation();
        entity.setLocationId(dto.getLocationId());
        entity.setLocationType(dto.getLocationType());
        entity.setAddressLine1(dto.getAddressLine1());
        entity.setAddressLine2(dto.getAddressLine2());
        entity.setCity(dto.getCity());
        entity.setPostCode(dto.getPostCode());
        entity.setCountry(dto.getCountry());
        entity.setPrice(dto.getPrice());
        entity.setParking(dto.isParking());
        entity.setPermitRequired(dto.isPermitRequired());
        entity.setRamp(dto.isRamp());
        entity.setLift(dto.isLift());
        entity.setDirect(dto.isDirect());
        entity.setKeywords(dto.getKeywords());
        return entity;
    }
}
