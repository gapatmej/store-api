package com.aperalta.store.service;

import com.aperalta.store.domain.AbstractMainEntity;

import java.util.Optional;

public interface AbstractService<E extends AbstractMainEntity> {
    E save(E e);
    Optional<E> findOne(Long id);
}
