package com.app.helpdesk.services;

import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ClienteDTO;
import com.app.helpdesk.domain.Pessoa;
import com.app.helpdesk.domain.enums.Perfil;
import com.app.helpdesk.exceptions.DataIntegrityViolationException;
import com.app.helpdesk.exceptions.ObjectNotFoundException;
import com.app.helpdesk.repositories.ClienteRepository;
import com.app.helpdesk.repositories.PessoaRepository;
import com.app.helpdesk.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements DefaultService<Cliente, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapperUtil mapperUtil;

    @Override
    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    @Override
    public Cliente create(Cliente entity) {
        entity.addPerfil(Perfil.CLIENTE);
        //validaPorCpfEEmail(entity);
        return clienteRepository.save(entity);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente update(Integer id, ClienteDTO entity) {
        entity.setId(id);
        Cliente obj = findById(id);
        validaPorCpfEEmail(entity);
        obj = mapperUtil.dtoForEntity(entity);
        return clienteRepository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }

        clienteRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO clienteDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(clienteDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != clienteDto.getId())
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");

        obj = pessoaRepository.findByEmail(clienteDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != clienteDto.getId())
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
    }
}
