package com.studentregsys;

import java.io.IOException;
import java.util.List;

public interface DataExport {
    void exportStudents(List<Student> students, String fileName) throws IOException;
    List<Student> importStudents(String fileName) throws IOException;
}