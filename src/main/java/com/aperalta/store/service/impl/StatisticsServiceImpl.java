package com.aperalta.store.service.impl;

import com.aperalta.store.domain.Statistics;
import com.aperalta.store.repository.StatisticsRepository;
import com.aperalta.store.repository.enumeration.ProductCategoryEnum;
import com.aperalta.store.service.StatisticsService;
import com.aperalta.store.service.dto.ProductDTO;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    private final EnumMap<ProductCategoryEnum, AtomicInteger> categoryMetrics = new EnumMap<>(ProductCategoryEnum.class);

    public StatisticsServiceImpl(MeterRegistry meterRegistry, StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
        for (ProductCategoryEnum category : ProductCategoryEnum.values()) {
            AtomicInteger metric = new AtomicInteger(0);
            categoryMetrics.put(category, metric);
            Gauge.builder("product_category_" + category.name().toLowerCase(), metric, AtomicInteger::get)
                    .description("Number of products in category " + category.getDisplayName())
                    .register(meterRegistry);
        }
    }

    @Async
    public void increaseStatistics(ProductDTO productDTO) {
        updateStatistics(productDTO,1);
    }

    @Async
    public void decreaseStatistics(ProductDTO productDTO) {
        updateStatistics(productDTO,-1);
    }


    private void updateStatistics(ProductDTO product, int value) {
        AtomicInteger metric = categoryMetrics.get(product.getProductCategory());
        if (metric != null) {
            metric.addAndGet(value);
        }
        Statistics statistics = statisticsRepository.findByProductCategory(product.getProductCategory());
        if (statistics == null) {
            statistics = new Statistics(product.getProductCategory(),value);
        } else {
            statistics.setTotal(statistics.getTotal()+value);
        }
        statisticsRepository.save(statistics);
    }

}
