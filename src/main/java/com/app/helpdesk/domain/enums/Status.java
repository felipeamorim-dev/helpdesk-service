package com.app.helpdesk.domain.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {
    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    ENCERRADO(2, "ENCERRADO");

    private final Integer codigo;
    private final String descricao;

    public static Status toEnum(Integer cod){
        if(cod == null) return null;

        for(Status per : Status.values()) {
            if (cod.equals(per.getCodigo())) return per;
        }

        throw new IllegalArgumentException("Status inválido");
    }
}
