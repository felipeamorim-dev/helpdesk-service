package com.app.helpdesk.services;

import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ClienteDTO;
import com.app.helpdesk.domain.Pessoa;

import java.util.List;
import java.util.Optional;

public interface DefaultService<E extends Pessoa, T> {

    E findById(Integer id);
    E create(E entity);
    List<E> findAll();
    E update(Integer id, T entity);
    void delete(Integer id);
}
