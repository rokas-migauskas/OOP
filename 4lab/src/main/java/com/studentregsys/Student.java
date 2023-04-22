package com.studentregsys;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Person {
    private String studentID;

    private Map<LocalDate, Boolean> attendance;

    private String groupName;

    public Student() {
    }

    public Student(String firstName, String lastName, String studentID) {
        super(firstName, lastName);
        this.studentID = studentID;
        this.attendance = new HashMap<>();

    }



    public boolean isPresent(LocalDate date) {
        return attendance.getOrDefault(date, false);
    }

    public void setPresent(LocalDate date, boolean present) {
        attendance.put(date, present);
    }


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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
