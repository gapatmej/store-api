package com.aperalta.store.service;

import com.aperalta.store.service.dto.AbstractMainDTO;

public interface AbstractServiceRest<D extends AbstractMainDTO> {

    D save(D entityDTO);
}
