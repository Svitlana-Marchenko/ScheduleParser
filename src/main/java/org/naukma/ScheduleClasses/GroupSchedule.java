package org.naukma.ScheduleClasses;

import java.util.List;
import java.util.Objects;

public class GroupSchedule {
    private String name;
    private List<TimeSlot> time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupSchedule that = (GroupSchedule) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public GroupSchedule(String name, List<TimeSlot> time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeSlot> getTime() {
        return time;
    }

    public void setTime(List<TimeSlot> time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GroupSchedule{" +
                "name='" + name + '\'' +
                ", time=" + time +
                '}';
    }
}
