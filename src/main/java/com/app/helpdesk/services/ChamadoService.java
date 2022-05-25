package com.app.helpdesk.services;

import com.app.helpdesk.domain.Chamado;
import com.app.helpdesk.domain.Dto.ChamadoDTO;

import java.util.List;

public interface ChamadoService {

    Chamado findById(Integer id);
    List<Chamado> findAll();
    Chamado create(ChamadoDTO chamadoDTO);
    Chamado update(Integer id, ChamadoDTO chamadoDTO);
}
