package com.example.shopbackend.service.CleanupServices;

import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeCleanupService {
    private final ProductAttributeRepository productAttributeRepository;

    public ProductAttributeCleanupService(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void cleanupUnreferencedProductAttributes() {
        productAttributeRepository.deleteAll(productAttributeRepository.findUnreferencedProductAttributes());
    }
}
