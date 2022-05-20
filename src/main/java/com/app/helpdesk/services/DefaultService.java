package com.app.helpdesk.services;

import java.util.List;
import java.util.Optional;

public interface DefaultService<E> {

    E findById(Integer id);
    E create(E entity);
    List<E> findAll();
    E update(Integer id, E entity);
    void delete(Integer id);
}
