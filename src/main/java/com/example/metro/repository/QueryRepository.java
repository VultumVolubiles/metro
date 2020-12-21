package com.example.metro.repository;

import com.example.metro.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QueryRepository extends JpaRepository<Query, Long> {
    Query findOneById(Long id);
}