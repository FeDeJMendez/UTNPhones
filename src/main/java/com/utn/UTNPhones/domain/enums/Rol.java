package com.utn.UTNPhones.domain.enums;

public enum Rol {
    ROLE_CLIENT,
    ROLE_BACKOFFICE;

    /*CLIENT ("client"),
    BACKOFFICE ("backoffice"),
    INFRASTRUCTURE ("infrastructure");

    private String description;

    Rol(String description) {
        this.description = description;
    }

    public static Rol find (final String value) {
        for (Rol r : values()) {
            if (r.toString().equalsIgnoreCase(value)) {
                return r;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid Rol: %s", value));
    }

    public String getDescription() {
        return description;
    }*/
}
