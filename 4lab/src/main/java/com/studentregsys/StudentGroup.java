package com.studentregsys;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class StudentGroup implements Group {
    private String groupName;
    private List<Student> students;

    public StudentGroup() {
        this("Default Group");
    }

    public StudentGroup(String groupName) {
        this.groupName = groupName;
        this.students = new ArrayList<>(); // Initialize the students list here
    }

    @Override
    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public void removeStudent(Student student) {
        students.remove(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    @Override
    public String toString() {
        return groupName; // assuming you have a groupName field in the StudentGroup class
    }
}
