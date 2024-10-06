package com.example.shopbackend.service.CleanupServices;

import com.example.shopbackend.repository.ProductRelatedRepositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CategoryCleanupService {
    private final CategoryRepository categoryRepository;

    public CategoryCleanupService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void cleanupUnreferencedCategories() {
        categoryRepository.deleteAll(categoryRepository.findUnreferencedCategories());
    }
}
