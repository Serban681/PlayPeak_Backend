package com.example.shopbackend.service.CleanupServices;

import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeRepository;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeValueRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeValueCleanupService {
    private final ProductAttributeValueRepository productAttributeValueRepository;

    public ProductAttributeValueCleanupService(ProductAttributeValueRepository productAttributeValueRepository) {
        this.productAttributeValueRepository = productAttributeValueRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void cleanupUnreferencedProductAttributeValues() {
        productAttributeValueRepository.deleteAll(productAttributeValueRepository.findUnreferencedProductAttributeValues());
    }
}
