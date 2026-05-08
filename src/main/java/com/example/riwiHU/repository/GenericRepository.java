package com.example.riwiHU.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository <T, ID>{
    T save(T entity);
    List<T> findAll();
    Optional<T> findById(ID id);
}
