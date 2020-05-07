package com.ben.StudentsBase.service;

import com.ben.StudentsBase.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
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

    public E findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public List<V> findAllViews() {
        return findAll().stream().map(toView).collect(Collectors.toList());
    }

    public V findViewById(Long id) {
        return repository
                .findById(id)
                .map(toView)
                .orElseThrow(() -> new RecordNotFoundException(id));

    }

    public E save(E entity) {
        return repository.save(entity);
    }

    public E saveView(V view) {
        return repository.save(toModel.apply(view));
    }

    public void deleteById(Long id) {
        E entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
        repository.delete(entity);
    }

    public D getRepository() {
        return repository;
    }

}
