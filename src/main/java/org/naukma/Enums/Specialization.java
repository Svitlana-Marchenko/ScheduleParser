package org.naukma.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Specialization {
    SOFTWARE_ENGINEERING("Інженерія програмного забезпечення"),
    ECONOMICS("Економіка"),
    MANAGEMENT("Менеджмент"),
    MARKETING("Маркетинг"),
    FINANCE_BANKING_AND_INSURANCE("Фінанси, банківська справа та страхування"),
    FE("Факультет економічних наук");

    private final String specialization;

    Specialization(String specialization) {
        this.specialization = specialization;
    }

    @JsonValue
    public String getSpecialization() {
        return specialization;
    }
    public static Specialization fromString(String specialization) {
        if (specialization != null) {
            switch (specialization.trim().toUpperCase()) {
                case "ІНЖЕНЕРІЯ ПРОГРАМНОГО ЗАБЕЗПЕЧЕННЯ":
                    return SOFTWARE_ENGINEERING;
                case "ЕКОНОМІКА ФІНАНСИ, БАНКІВСЬКА СПРАВА ТА СТРАХУВАННЯ МАРКЕТИНГ МЕНЕДЖМЕНТ":
                    return FE;

            }
        }
        throw new IllegalArgumentException("Invalid or null specialization: " + specialization);
    }
}