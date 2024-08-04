package com.aperalta.store.domain;

import com.aperalta.store.repository.enumeration.ProductCategoryEnum;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "statistics")
public class Statistics implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category", nullable = false)
    private ProductCategoryEnum productCategory;

    @Column(name = "total", nullable = false)
    private int total;

    public Statistics(ProductCategoryEnum productCategory, int total) {
        this.productCategory = productCategory;
        this.total = total;
    }

    public Statistics() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategoryEnum getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryEnum productCategory) {
        this.productCategory = productCategory;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
