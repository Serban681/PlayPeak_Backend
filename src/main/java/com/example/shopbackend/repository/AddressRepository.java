package com.example.shopbackend.repository;


import com.example.shopbackend.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.id NOT IN (SELECT u.default_delivery_address.id FROM User u WHERE u.default_delivery_address IS NOT NULL) AND a.id NOT IN (SELECT u.default_billing_address.id FROM User u WHERE u.default_billing_address IS NOT NULL)")
    List<Address> findUnreferencedAddresses();

    @Query("SELECT a FROM Address a WHERE a.streetLine = ?1 AND a.postalCode = ?2 AND a.city = ?3 AND a.county = ?4 AND a.country = ?5")
    List<Address> findAddressByProperties(String streetLine, int postalCode, String city, String county, String country);
}
