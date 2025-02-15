package com.example.shopbackend.service;

import com.example.shopbackend.dto.AddressDto;
import com.example.shopbackend.entity.Address;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.AddressMapper;
import com.example.shopbackend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public AddressService(AddressMapper addressMapper, AddressRepository addressRepository) {
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    public AddressDto getOneById(int id) {
        return addressMapper.toDto(addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address", id)));
    }

    public List<AddressDto> getAll() {
        return StreamSupport.stream(addressRepository.findAll().spliterator(), false).map(addressMapper::toDto).toList();
    }

    public AddressDto create(AddressDto addressDto) {
        List<Address> existingAddresses = addressRepository.findAddressByProperties(addressDto.getStreetLine(), addressDto.getPostalCode(), addressDto.getCity(), addressDto.getCounty(), addressDto.getCountry());

        if(!existingAddresses.isEmpty()) {
            return addressMapper.toDto(existingAddresses.getFirst());
        }

        return addressMapper.toDto(addressRepository.save(addressMapper.toEntity(addressDto)));
    }

    public void delete(int id) {
        addressRepository.deleteById(id);
    }
}
