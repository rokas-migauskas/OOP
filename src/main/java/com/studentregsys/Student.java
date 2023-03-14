package com.studentregsys;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person {
    private String studentID;
    private Map<LocalDate, Boolean> attendance;
    private BooleanProperty present;

    public Student(String firstName, String lastName, String studentID) {
        super(firstName, lastName);
        this.studentID = studentID;
        this.attendance = new HashMap<>();
        this.present = new SimpleBooleanProperty(false);
    }

    // Getters, setters, and methods to manage attendance

    public boolean isPresent() {
        return present.get();
    }

    public BooleanProperty presentProperty() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present.set(present);
    }

    // Add this new method
    public void setAttendance(LocalDate date, boolean isPresent) {
        attendance.put(date, isPresent);
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Map<LocalDate, Boolean> getAttendance() {
        return attendance;
    }

    public void setAttendance(Map<LocalDate, Boolean> attendance) {
        this.attendance = attendance;
    }
}
