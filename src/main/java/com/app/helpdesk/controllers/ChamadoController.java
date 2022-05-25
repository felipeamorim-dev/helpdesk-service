package com.app.helpdesk.controllers;

import com.app.helpdesk.domain.Chamado;
import com.app.helpdesk.domain.Dto.ChamadoDTO;
import com.app.helpdesk.services.ChamadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), ChamadoDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll(){
        return ResponseEntity.ok().body(
                service.findAll()
                        .stream()
                        .map(chamado -> mapper.map(chamado, ChamadoDTO.class))
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO){
        Chamado newChamado = service.create(chamadoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newChamado.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO chamadoDTO){
        Chamado newChamado = service.update(id, chamadoDTO);
        return ResponseEntity.ok().body(mapper.map(newChamado, ChamadoDTO.class));
    }

 }
