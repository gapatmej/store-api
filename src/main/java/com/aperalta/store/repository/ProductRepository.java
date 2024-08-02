package com.aperalta.store.repository;

import com.aperalta.store.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepositoryCustom<Product, Long> {
}
