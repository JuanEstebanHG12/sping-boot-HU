package com.example.riwiHU.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository <T, ID> extends JpaRepository<T, ID> {
    //T save(T entity);
    //List<T> findAll();
    Optional<T> findById(ID id);
}
