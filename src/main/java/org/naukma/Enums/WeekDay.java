package org.naukma.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeekDay {
    MONDAY("Понеділок"),
    TUESDAY("Вівторок"),
    WEDNESDAY("Середа"),
    THURSDAY("Четвер"),
    FRIDAY("Пʼятниця"),
    SATURDAY("Субота");

    private final String day;

    WeekDay(String day) {
        this.day = day;
    }

    @JsonValue
    public String getDay() {
        return day;
    }
    public static WeekDay fromString(String day) {
        if (day != null) {
            switch (day.trim().toUpperCase()) {
                case "ПОНЕДІЛОК":
                    return MONDAY;
                case "ВІВТОРОК":
                    return TUESDAY;
                case "СЕРЕДА":
                    return WEDNESDAY;
                case "ЧЕТВЕР":
                    return THURSDAY;
                case "П`ЯТНИЦЯ", "П’ЯТНИЦЯ":
                    return FRIDAY;
                case "СУБОТА":
                    return SATURDAY;
            }
        }
        throw new IllegalArgumentException("Invalid or null day: " + day);
    }
}
