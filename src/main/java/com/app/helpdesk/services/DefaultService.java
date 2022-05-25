package com.app.helpdesk.services;

import com.app.helpdesk.domain.Pessoa;
import java.util.List;

public interface DefaultService<E extends Pessoa, T extends Object> {

    E findById(Integer id);
    E create(T entityDto);
    List<E> findAll();
    E update(Integer id, T entityDto);
    void delete(Integer id);
}
