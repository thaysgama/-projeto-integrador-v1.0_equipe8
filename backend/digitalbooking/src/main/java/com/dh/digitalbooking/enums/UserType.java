package com.dh.digitalbooking.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserType {

    ADMINISTRADOR (Values.ADMINISTRADOR),
    CLIENTE(Values.CLIENTE),
    PROPRIETARIO(Values.PROPRIETARIO);

    private String value;

    public static class Values {
        public static final String ADMINISTRADOR = "A";
        public static final String CLIENTE = "C";
        public static final String PROPRIETARIO = "P";
    }
}
