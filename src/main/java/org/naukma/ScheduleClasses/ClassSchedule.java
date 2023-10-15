package org.naukma.ScheduleClasses;

import org.naukma.Enums.Faculty;
import org.naukma.Enums.Specialization;

import java.util.*;

public class ClassSchedule {
    private Faculty faculty;
    private Specialization specialization;
    private String subjectName;
    private int yearOfStudying;
    private Map<String, List<TimeSlot>> groups;

    public ClassSchedule(Faculty faculty, Specialization specialization, String subjectName, int yearOfStudying, HashMap<String, List <TimeSlot>> groups) {
        this.faculty = faculty;
        this.specialization = specialization;
        this.subjectName = subjectName;
        this.yearOfStudying = yearOfStudying;
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassSchedule that = (ClassSchedule) o;
        return yearOfStudying == that.yearOfStudying && faculty == that.faculty && specialization == that.specialization && Objects.equals(subjectName, that.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faculty, specialization, subjectName, yearOfStudying);
    }



    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getYearOfStudying() {
        return yearOfStudying;
    }

    public void setYearOfStudying(int yearOfStudying) {
        this.yearOfStudying = yearOfStudying;
    }

    public Map<String, List <TimeSlot>> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, List <TimeSlot>> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "ClassSchedule{" +
                "faculty=" + faculty +
                ", specialization=" + specialization +
                ", subjectName='" + subjectName + '\'' +
                ", yearOfStudying=" + yearOfStudying +
                ", groups=" + groups +
                '}';
    }
}
