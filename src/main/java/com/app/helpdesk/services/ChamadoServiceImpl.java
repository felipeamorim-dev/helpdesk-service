package com.app.helpdesk.services;

import com.app.helpdesk.domain.Chamado;
import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ChamadoDTO;
import com.app.helpdesk.domain.Tecnico;
import com.app.helpdesk.domain.enums.Prioridade;
import com.app.helpdesk.domain.enums.Status;
import com.app.helpdesk.exceptions.ObjectNotFoundException;
import com.app.helpdesk.repositories.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChamadoServiceImpl implements ChamadoService{

    @Autowired
    private ChamadoRepository repository;
    @Autowired
    private TecnicoServiceImpl tecnicoService;
    @Autowired
    private ClienteServiceImpl clienteService;

    @Override
    public Chamado findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    @Override
    public List<Chamado> findAll() {
        return repository.findAll();
    }

    @Override
    public Chamado create(ChamadoDTO chamadoDTO) {
        return repository.save(newChamado(chamadoDTO));
    }

    @Override
    public Chamado update(Integer id, ChamadoDTO chamadoDTO) {
        chamadoDTO.setId(id);
        Chamado chamadoOld = findById(id);
        chamadoOld = newChamado(chamadoDTO);
        return repository.save(chamadoOld);
    }

    private Chamado newChamado(ChamadoDTO chamadoDTO){
        Tecnico tecnico = tecnicoService.findById(chamadoDTO.getTecnicoId());
        Cliente cliente = clienteService.findById(chamadoDTO.getClienteId());
        Chamado chamado = new Chamado();

        if(chamadoDTO.getId() != null) chamado.setId(chamadoDTO.getId());
        if(chamadoDTO.getStatus().equals(2)) chamado.setDataFechamento(LocalDate.now());

        chamado.setCliente(cliente);
        chamado.setTecnico(tecnico);
        chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setObservacoes(chamadoDTO.getObservacoes());
        return  chamado;
    }
}
