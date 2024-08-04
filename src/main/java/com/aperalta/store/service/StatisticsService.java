package com.aperalta.store.service;

import com.aperalta.store.service.dto.ProductDTO;

public interface StatisticsService {
    void increaseStatistics(ProductDTO product);
    void decreaseStatistics(ProductDTO product);
}
