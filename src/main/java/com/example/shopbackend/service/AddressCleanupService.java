package com.example.shopbackend.service;

import com.example.shopbackend.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AddressCleanupService {
    private final AddressRepository addressRepository;

    public AddressCleanupService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void cleanupUnreferencedAddresses() {
        addressRepository.deleteAll(addressRepository.findUnreferencedAddresses());
    }
}
