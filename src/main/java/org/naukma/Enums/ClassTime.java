package org.naukma.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClassTime {
    TIME_8_30_TO_9_50("8:30-9:50"),
    TIME_10_00_TO_11_20("10:00-11:20"),
    TIME_11_40_TO_13_00("11:40-13:00"),
    TIME_13_30_TO_14_50("13:30-14:50"),
    TIME_15_00_TO_16_20("15:00-16:20"),
    TIME_16_30_TO_17_50("16:30-17:50"),
    TIME_18_00_TO_19_20("18:00-19:20");

    private final String classTime;

    ClassTime(String classTime) {
        this.classTime = classTime;
    }

    @JsonValue
    public String getClassTime() {
        return classTime;
    }
    public static ClassTime fromString(String time) {
        if (time != null) {
            switch (time) {
                case "8:30-9:50", "8.30-9.50":
                    return TIME_8_30_TO_9_50;
                case "10:00-11:20", "10.00-11.20":
                    return TIME_10_00_TO_11_20;
                case "11:40-13:00", "11.40-13.00":
                    return TIME_11_40_TO_13_00;
                case "13:30-14:50", "13.30-14.50":
                    return TIME_13_30_TO_14_50;
                case "15:00-16:20", "15.00-16.20":
                    return TIME_15_00_TO_16_20;
                case "16:30-17:50", "16.30-17.50":
                    return TIME_16_30_TO_17_50;
                case "18:00-19:20", "18.00-19.20":
                    return TIME_18_00_TO_19_20;
            }
        }
        throw new IllegalArgumentException("Invalid or null time: " + time);
    }
    }