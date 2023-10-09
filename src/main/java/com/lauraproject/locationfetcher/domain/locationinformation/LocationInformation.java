package com.lauraproject.locationfetcher.domain.locationinformation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_location_information")
@Builder
@Getter
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
}
