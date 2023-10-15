package org.naukma.ScheduleClasses;

import org.naukma.Enums.ClassTime;
import org.naukma.Enums.WeekDay;

public class TimeSlot {
    private WeekDay day;
    private ClassTime time;
    private String room;
    private String weeks;

    public TimeSlot(WeekDay day, ClassTime time, String room, String weeks) {
        this.day = day;
        this.time = time;
        this.room = room;
        this.weeks = weeks;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public ClassTime getTime() {
        return time;
    }

    public void setTime(ClassTime time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "day=" + day +
                ", time=" + time +
                ", room='" + room + '\'' +
                ", weeks='" + weeks + '\'' +
                '}';
    }
}
