package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="street_line", nullable=false)
    private String streetLine;

    @Column(name="postal_code", nullable=false)
    private int postalCode;

    @Column(nullable=false)
    private String city;

    @Column(nullable=false)
    private String county;

    @Column(name="country", nullable=false)
    private String country;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetLine, address.streetLine) && postalCode == address.postalCode && Objects.equals(city, address.city) && Objects.equals(county, address.county) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetLine, postalCode, city, county, country);
    }
}
