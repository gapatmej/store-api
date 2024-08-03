package com.aperalta.store.repository;

import com.aperalta.store.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {}
