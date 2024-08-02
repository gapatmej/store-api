package com.aperalta.store.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product extends AbstractMainEntity  implements Serializable {

    @Column(name = "main_code", length = 50, nullable = false, unique = true)
    private String mainCode;

    @Column(name = "auxiliary_code", length = 50, unique = true)
    private String auxiliaryCode;

    @Column(name = "barcode", length = 50, unique = true)
    private String barcode;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "price", precision = 9, scale = 4, nullable = false)
    private BigDecimal price;

    @Column(name = "attribute_1", length = 100)
    private String attribute1;

    @Column(name = "attribute_2", length = 100)
    private String attribute2;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode;
    }

    public String getAuxiliaryCode() {
        return auxiliaryCode;
    }

    public void setAuxiliaryCode(String auxiliaryCode) {
        this.auxiliaryCode = auxiliaryCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
