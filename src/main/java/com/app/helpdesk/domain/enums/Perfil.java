package com.app.helpdesk.domain.enums;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Perfil {
    ADMIN(0, "ROLE_ADMIN"),
    CLIENTE(1, "ROLE_CLIENTE"),
    TECNICO(2, "ROLE_TECNICO");

    private final Integer codigo;
    private final String descricao;

    public static Perfil toEnum(Integer cod){
        if(cod == null) return null;

        for(Perfil per : Perfil.values()) {
            if (cod.equals(per.getCodigo())) return per;
        }

        throw new IllegalArgumentException("Perfil inválido");
    }
}
