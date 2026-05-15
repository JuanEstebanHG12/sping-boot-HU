package com.example.riwiHU.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenericRepository <T, ID> {
    T save(T entity);
    List<T> findAll();

    Optional<T> findById(ID id);
}
