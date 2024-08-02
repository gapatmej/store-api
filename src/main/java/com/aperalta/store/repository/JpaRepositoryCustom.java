package com.aperalta.store.repository;

import com.aperalta.store.domain.AbstractMainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaRepositoryCustom <T extends AbstractMainEntity, Q> extends JpaRepository<T,Q>, JpaSpecificationExecutor<T>  {
}
