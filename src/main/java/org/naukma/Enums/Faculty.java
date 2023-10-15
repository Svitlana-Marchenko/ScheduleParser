package org.naukma.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Faculty {
    FACULTY_OF_COMPUTER_SCIENCES("Факультет інформатики"),
    FACULTY_OF_ECONOMICS("Факультет економічних наук");

    private final String faculty;

    Faculty(String faculty) {
        this.faculty = faculty;
    }

    @JsonValue
    public String getFaculty() {
        return faculty;
    }
    public static Faculty fromString(String faculty) {
        if (faculty != null) {
            switch (faculty.trim().toUpperCase()) {
                case "ФАКУЛЬТЕТ ІНФОРМАТИКИ":
                    return FACULTY_OF_COMPUTER_SCIENCES;
                case "ФАКУЛЬТЕТ ЕКОНОМІЧНИХ НАУК":
                    return FACULTY_OF_ECONOMICS;
            }
        }
        throw new IllegalArgumentException("Invalid or null faculty: " + faculty);
    }
}
