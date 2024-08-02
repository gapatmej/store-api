package com.aperalta.store.service;

import com.aperalta.store.domain.AbstractMainEntity;

public interface AbstractService<E extends AbstractMainEntity> {

    E save(E e);
}
