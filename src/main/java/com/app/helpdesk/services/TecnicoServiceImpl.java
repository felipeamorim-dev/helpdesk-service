package com.app.helpdesk.services;

import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ClienteDTO;
import com.app.helpdesk.domain.Dto.TecnicoDTO;
import com.app.helpdesk.domain.Pessoa;
import com.app.helpdesk.domain.Tecnico;
import com.app.helpdesk.domain.enums.Perfil;
import com.app.helpdesk.exceptions.DataIntegrityViolationException;
import com.app.helpdesk.exceptions.ObjectNotFoundException;
import com.app.helpdesk.repositories.ClienteRepository;
import com.app.helpdesk.repositories.PessoaRepository;
import com.app.helpdesk.repositories.TecnicoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoServiceImpl implements DefaultService<Tecnico, TecnicoDTO> {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Tecnico findById(Integer id) {
        return tecnicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    @Override
    public Tecnico create(TecnicoDTO entityDto) {
        entityDto.setSenha(encoder.encode(entityDto.getSenha()));
        validaPorCpfEEmail(entityDto);
        Tecnico tecnico = modelMapper.map(entityDto, Tecnico.class);
        tecnico.addPerfil(Perfil.TECNICO);
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    @Override
    public Tecnico update(Integer id, TecnicoDTO entityDto) {
        entityDto.setId(id);
        Tecnico tecnicoAtualizado = findById(id);

        //Encode da senha atualizada
        if(!entityDto.getSenha().equals(tecnicoAtualizado.getSenha())) entityDto.setSenha(encoder.encode(entityDto.getSenha()));

        validaPorCpfEEmail(entityDto);
        modelMapper.map(modelMapper.map(entityDto, Tecnico.class), tecnicoAtualizado);
        return tecnicoRepository.save(tecnicoAtualizado);
    }

    @Override
    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }

        tecnicoRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(TecnicoDTO entityDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(entityDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != entityDto.getId())
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");

        obj = pessoaRepository.findByEmail(entityDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != entityDto.getId())
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
    }
}
