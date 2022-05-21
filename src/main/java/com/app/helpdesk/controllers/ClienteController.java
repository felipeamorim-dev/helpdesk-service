package com.app.helpdesk.controllers;

import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ClienteDTO;
import com.app.helpdesk.domain.enums.Perfil;
import com.app.helpdesk.services.ClienteService;
import com.app.helpdesk.util.MapperUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MapperUtil mapperUtil;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.map(clienteService.findById(id), ClienteDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(){
        return ResponseEntity.ok(clienteService.findAll()
                .stream()
                .map(cliente -> mapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO obj){
        Cliente newCliente = clienteService.create(mapper.map(obj, Cliente.class));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO obj){
        obj.setId(null);
        Cliente clienteAtualizado = clienteService.update(id, obj);
        return ResponseEntity.ok().body(mapper.map(clienteAtualizado, ClienteDTO.class));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
