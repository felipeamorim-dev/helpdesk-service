package com.app.helpdesk.controllers;

import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ClienteDTO;
import com.app.helpdesk.domain.Dto.TecnicoDTO;
import com.app.helpdesk.domain.Tecnico;
import com.app.helpdesk.services.ClienteServiceImpl;
import com.app.helpdesk.services.TecnicoServiceImpl;
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
@RequestMapping(path = "/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoServiceImpl tecnicoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(path = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.map(tecnicoService.findById(id), TecnicoDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){
        return ResponseEntity.ok(tecnicoService.findAll()
                .stream()
                .map(tecnico -> mapper.map(tecnico, TecnicoDTO.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody TecnicoDTO obj){
        Tecnico newTecnico = tecnicoService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTecnico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO obj){
        obj.setId(null);
        Tecnico tecnicoAtualizado = tecnicoService.update(id, obj);
        return ResponseEntity.ok().body(mapper.map(tecnicoAtualizado, TecnicoDTO.class));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tecnicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
