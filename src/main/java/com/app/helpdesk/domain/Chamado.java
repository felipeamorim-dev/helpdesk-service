package com.app.helpdesk.domain;

import com.app.helpdesk.domain.enums.Prioridade;
import com.app.helpdesk.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_CHAMADO")
public class Chamado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_ABERTURA")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_FECHAMENTO")
    private LocalDate dataFechamento;

    @Column(name = "PRIORIDADE")
    private Prioridade prioridade;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "OBSERVACOES")
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "TECNICO_ID", referencedColumnName = "ID")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
    private Cliente cliente;

    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Cliente cliente) {
        this.id = id;
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }
}
