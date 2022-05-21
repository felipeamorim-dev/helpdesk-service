package com.app.helpdesk.util;

import com.app.helpdesk.domain.Cliente;
import com.app.helpdesk.domain.Dto.ClienteDTO;
import com.app.helpdesk.domain.Pessoa;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MapperUtil {

    public Cliente dtoForEntity(ClienteDTO dto){
        Cliente entity = new Cliente();
        if(!ObjectUtils.isEmpty(dto.getId())) entity.setId(dto.getId());
        if(!ObjectUtils.isEmpty(dto.getNome())) entity.setNome(dto.getNome());
        if(!ObjectUtils.isEmpty(dto.getCpf())) entity.setCpf(dto.getCpf());
        if(!ObjectUtils.isEmpty(dto.getEmail())) entity.setEmail(dto.getEmail());
        if(!ObjectUtils.isEmpty(dto.getSenha())) entity.setSenha(dto.getSenha());
        if(dto.getPerfis().size() > 0) entity.addPerfis(dto.getPerfis());
        if(!ObjectUtils.isEmpty(dto.getDataCriacao())) entity.setDataCriacao(dto.getDataCriacao());

        return entity;
    }
}
