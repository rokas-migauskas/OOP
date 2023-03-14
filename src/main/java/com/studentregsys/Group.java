package com.studentregsys;

import java.util.List;

public interface Group {
    void addStudent(Student student);
    void removeStudent(Student student);
    List<Student> getStudents();
}
