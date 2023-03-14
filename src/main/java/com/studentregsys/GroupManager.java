package com.studentregsys;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    private List<StudentGroup> studentGroups = new ArrayList<>();


    public GroupManager() {

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
}
