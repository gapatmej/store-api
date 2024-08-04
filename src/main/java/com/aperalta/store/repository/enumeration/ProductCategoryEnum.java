package com.aperalta.store.repository.enumeration;

public enum ProductCategoryEnum {

    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    BOOKS("Books"),
    TOYS("Toys"),
    BEAUTY("Beauty"),
    SPORTS("Sports");

    private final String displayName;

    ProductCategoryEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
