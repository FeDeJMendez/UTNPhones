package com.utn.UTNPhones.domain.enums;

public enum Rol {
    CLIENT/*("Client")*/,
    BACKOFFICE/*("Employee")*/;

/*    private String description;

    Rol(String description) {
        this.description = description;
    }

    public static Rol find (final String value) {
        for (Rol r : values()) {
            if (value.toString().equalsIgnoreCase(value)) {
                return r;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid Rol: %s", value));
    }

    public String getDescription() {
        return description;
    }*/
}
