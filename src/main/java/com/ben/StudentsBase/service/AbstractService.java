package com.ben.StudentsBase.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractService<D extends JpaRepository<E, Long>, E, V> {
    private final D repository;
    private final Function<E, V> toView;
    private final Function<V, E> toModel;

    protected AbstractService(D repository, Function<E, V> toView, Function<V, E> toModel) {
        this.repository = repository;
        this.toView = toView;
        this.toModel = toModel;
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    public List<V> findAllViews() {
        return findAll().stream().map(toView).collect(Collectors.toList());
    }

    public Optional<V> findViewById(Long id) {
        return repository.findById(id).map(toView);
    }

    public void save(E entity) {
        repository.save(entity);
    }

    public void saveView(V view) {
        repository.save(toModel.apply(view));
    }

    public D getRepository() {
        return repository;
    }


}
