package com.app.helpdesk.domain;

import com.app.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.app.helpdesk.domain.enums.Perfil.*;

@Data
@Entity
@Table(name = "TB_PESSOA")
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Integer id;

    @Column(name = "NOME")
    protected String nome;

    @Column(name = "CPF", unique = true)
    protected String cpf;

    @Column(name = "EMAIL", unique = true)
    protected String email;

    @Column(name = "SENHA")
    protected String senha;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_CRIACAO")
    protected LocalDate dataCriacao = LocalDate.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    protected Set<Integer> perfis = new HashSet<>();

    public Pessoa(){
        addPerfil(CLIENTE);
    }

    public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        addPerfil(CLIENTE);
    }

    public Set<Perfil> getPerfis(){
        return perfis.stream().map(perfil -> toEnum(perfil)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil){
        this.perfis.add(perfil.getCodigo());
    }

    public void addPerfis(Set<Perfil> perfis) {
        perfis.forEach(perfil -> this.perfis.add(perfil.getCodigo()));
    }
}
