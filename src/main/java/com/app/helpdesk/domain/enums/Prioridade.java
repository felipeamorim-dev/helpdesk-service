package com.app.helpdesk.domain.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Prioridade {
    BAIXA(0, "BAIXA"),
    MEDIA(1, "MEDIA"),
    ALTA(2, "ALTA");

    private final Integer codigo;
    private final String descricao;

    public static Prioridade toEnum(Integer cod){
        if(cod == null) return null;

        for(Prioridade per : Prioridade.values()) {
            if (cod.equals(per.getCodigo())) return per;
        }

        throw new IllegalArgumentException("Prioridade inválido");
    }
}
