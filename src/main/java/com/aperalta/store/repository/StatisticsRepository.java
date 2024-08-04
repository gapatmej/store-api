package com.aperalta.store.repository;

import com.aperalta.store.domain.Statistics;
import com.aperalta.store.repository.enumeration.ProductCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics,Long> {
    Statistics findByProductCategory(ProductCategoryEnum productCategoryEnum);
}
