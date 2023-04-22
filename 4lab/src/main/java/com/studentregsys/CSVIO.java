package com.studentregsys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVIO implements DataIO {

    private DataManager dataManager;
    private static final String CSV_SEPARATOR = ",";

    public CSVIO() {
        dataManager = DataManager.getInstance();
    }

    @Override
    public void exportStudents(List<Student> students, String fileName) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            String header = "StudentID,FirstName,LastName,GroupName";
            bw.write(header);
            bw.newLine();

            for (Student student : students) {
                String studentID = student.getStudentID();
                String firstName = student.getFirstName();
                String lastName = student.getLastName();
                String groupName = student.getGroupName();

                String line = String.join(",", studentID, firstName, lastName, groupName);
                bw.write(line);
                bw.newLine();
            }
        }
    }

    @Override
    public List<Student> importStudents(String fileName) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String studentID = fields[0];
                String firstName = fields[1];
                String lastName = fields[2];
                String groupName = fields[3];

                Student student = new Student(firstName, lastName, studentID);
                student.setGroupName(groupName);
                students.add(student);
            }
        }

        return students;
    }
}
