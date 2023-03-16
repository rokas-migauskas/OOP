package com.studentregsys;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    private List<StudentGroup> studentGroups = new ArrayList<>();


    public GroupManager() {

    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void addStudentGroup(StudentGroup studentGroup) {
        studentGroups.add(studentGroup);
    }

    public void removeStudentGroup(StudentGroup studentGroup) {
        studentGroups.remove(studentGroup);
    }

    public StudentGroup findGroupByName(String groupName) {
        for (StudentGroup group : studentGroups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "GroupManager{" +
                "studentGroups=" + studentGroups +
                '}';
    }

    public List<Student> getAllStudents() {
        List<Student> allStudents = new ArrayList<>();
        for (StudentGroup group : studentGroups) {
            allStudents.addAll(group.getStudents());
        }
        return allStudents;
    }

}
