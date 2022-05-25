package com.app.helpdesk.config;

import com.app.helpdesk.domain.Chamado;
import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ChamadoDTO;
import com.app.helpdesk.domain.Dto.ClienteDTO;
import com.app.helpdesk.domain.Dto.TecnicoDTO;
import com.app.helpdesk.domain.Tecnico;
import com.app.helpdesk.domain.enums.Perfil;
import com.app.helpdesk.domain.enums.Prioridade;
import com.app.helpdesk.domain.enums.Status;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;
import java.util.stream.Collectors;


@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // Realiza a converção da collection de perfil em uma collection de integer, dessa forma, é possível realizar a correspondência de métodos entre o DTO de clientes e Clientes.
        Converter<Set<Perfil>, Set<Integer>> perfilSetConverter = cxt -> cxt.getSource().stream().map(perfil -> perfil.getCodigo()).collect(Collectors.toSet());
        modelMapper.createTypeMap(ClienteDTO.class, Cliente.class).addMappings(mapper -> mapper.using(perfilSetConverter).map(ClienteDTO::getPerfis, Cliente::setPerfis));
        modelMapper.createTypeMap(TecnicoDTO.class, Tecnico.class).addMappings(mapper -> mapper.using(perfilSetConverter).map(TecnicoDTO::getPerfis, Tecnico::setPerfis));

        // Configuração do mapeamento entidade clientes para realizar atualizações de dados da entidade.
        modelMapper.createTypeMap(Cliente.class, Cliente.class).addMappings(mapper -> mapper.using(perfilSetConverter).map(Cliente::getPerfis, Cliente::setPerfis));
        modelMapper.createTypeMap(Tecnico.class, Tecnico.class).addMappings(mapper -> mapper.using(perfilSetConverter).map(Tecnico::getPerfis, Tecnico::setPerfis));

        // Mapeamento para a converção de chamado para chamadoDto
        Converter<Prioridade, Integer> prioridadeIntegerConverter = cxt -> cxt.getSource().getCodigo();
        Converter<Status, Integer> statusIntegerConverter = cxt -> cxt.getSource().getCodigo();
        modelMapper.createTypeMap(Chamado.class, ChamadoDTO.class)
                .addMappings(mapper -> mapper.using(prioridadeIntegerConverter).map(Chamado::getPrioridade, ChamadoDTO::setPrioridade))
                .addMappings(mapper -> mapper.using(statusIntegerConverter).map(Chamado::getStatus, ChamadoDTO::setStatus));

        return modelMapper;
    }

}
