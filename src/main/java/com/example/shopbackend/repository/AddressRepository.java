package com.example.shopbackend.repository;


import com.example.shopbackend.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.id NOT IN (SELECT u.default_delivery_address.id FROM User u WHERE u.default_delivery_address IS NOT NULL) AND a.id NOT IN (SELECT u.default_billing_address.id FROM User u WHERE u.default_billing_address IS NOT NULL)")
    List<Address> findUnreferencedAddresses();

    @Query("SELECT a FROM Address a WHERE a.country = :country AND a.streetLine = :streetLine AND a.postalCode = :postalCode AND a.city = :city AND a.county = :county")
    List<Address> findAddressByProperties(@Param("streetLine") String streetLine, @Param("postalCode") int postalCode, @Param("city") String city, @Param("county") String county, @Param("country") String country);//String streetLine, int postalCode, String city, String county, String country);
}
