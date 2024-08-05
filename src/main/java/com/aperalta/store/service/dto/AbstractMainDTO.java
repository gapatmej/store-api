package com.aperalta.store.service.dto;

public class AbstractMainDTO<T> {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public T id(Long id) {
        this.id = id;
        return (T) this;
    }
}
