package com.aperalta.store.service.dto;

import com.aperalta.store.repository.enumeration.ProductCategoryEnum;
import com.aperalta.store.web.rest.validators.NullOrNotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductDTO extends AbstractMainDTO<ProductDTO> {

    @NotNull(message = "mainCode cannot be null")
    private String mainCode;

    @NullOrNotBlank(message = "auxiliaryCode cannot be blank")
    private String auxiliaryCode;

    @NullOrNotBlank(message = "barcode cannot be blank")
    private String barcode;

    @NotNull(message = "productCategory cannot be null")
    private ProductCategoryEnum productCategory;

    @NotNull(message = "mainCode cannot be null")
    private String name;

    @NotNull(message = "price cannot be null")
    private BigDecimal price;

    private String attribute1;

    private String attribute2;

    @NotNull(message = "active cannot be null")
    private boolean active;

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode;
    }
    public ProductDTO mainCode(String mainCode) {
        this.mainCode = mainCode;
        return this;
    }

    public String getAuxiliaryCode() {
        return auxiliaryCode;
    }

    public void setAuxiliaryCode(String auxiliaryCode) {
        this.auxiliaryCode = auxiliaryCode;
    }
    public ProductDTO auxiliaryCode(String auxiliaryCode) {
        this.auxiliaryCode = auxiliaryCode;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public ProductDTO barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public ProductCategoryEnum getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryEnum productCategory) {
        this.productCategory = productCategory;
    }
    public ProductDTO productCategory(ProductCategoryEnum productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ProductDTO name(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public ProductDTO price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
    public ProductDTO attribute1(String attribute1) {
        this.attribute1 = attribute1;
        return  this;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }
    public ProductDTO attribute2(String attribute2) {
        this.attribute2 = attribute2;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public ProductDTO active(boolean active) {
        this.active = active;
        return this;
    }
}
