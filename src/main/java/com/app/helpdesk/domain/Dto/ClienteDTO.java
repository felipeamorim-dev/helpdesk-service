package com.app.helpdesk.domain.Dto;

import com.app.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.app.helpdesk.domain.enums.Perfil.toEnum;

@Data
@NoArgsConstructor
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;

    @NotNull(message = "O campo NOME é requerido")
    protected String nome;

    @NotNull(message = "O campo EMAIL é requerido")
    protected String email;

    @CPF
    @NotNull(message = "O campo CPF é requerido")
    protected String cpf;

    @NotNull(message = "O campo SENHA é requerido")
    protected String senha;

    protected Set<Perfil> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

}
