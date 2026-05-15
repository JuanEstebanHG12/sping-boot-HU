package com.example.riwiHU.repository;

import java.util.*;
import java.util.function.Function;

public class GenericMemoryRepositoryImpl<T, ID> implements GenericRepository<T, ID> {

    private final Map<ID, T> data = new HashMap<>();
    private final Function<T, ID> getId;

    public GenericMemoryRepositoryImpl(Function<T, ID> getId) {
        this.getId = getId;
    }


    @Override
    public T save(T entity) {
        ID id = getId.apply(entity);
        data.put(id, entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(data.get(id));
    }
}
