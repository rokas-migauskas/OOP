package com.studentregsys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataExport {

    public void exportStudents(List<Student> students, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String header = "First Name,Last Name,Student ID\n";
            writer.write(header);

            for (Student student : students) {
                String row = student.getFirstName() + "," + student.getLastName() + "," + student.getStudentID() + "\n";
                writer.write(row);
            }
        }
    }

    public List<Student> importStudents(String fileName) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String firstName = data[0].trim();
                String lastName = data[1].trim();
                String studentID = data[2].trim();

                students.add(new Student(firstName, lastName, studentID));
            }
        }

        return students;
    }
}
