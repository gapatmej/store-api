package com.aperalta.store.service;

import com.aperalta.store.service.dto.AbstractMainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AbstractServiceRest<D extends AbstractMainDTO> {

    D save(D entityDTO);
    Page<D> findAll(String search, Pageable pageable);

    Optional<D> findOneDto(Long id);

    Optional<D> delete(Long id);
}
