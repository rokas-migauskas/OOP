package com.studentregsys;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelIO implements DataIO {
    private DataManager dataManager;

    public ExcelIO() {
        dataManager = DataManager.getInstance();
    }

    @Override
    public void exportStudents(List<Student> students, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headerColumns = {"StudentID", "FirstName", "LastName", "GroupName"};
            for (int i = 0; i < headerColumns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerColumns[i]);
            }

            int rowNum = 1;
            for (Student student : students) {
                String studentID = student.getStudentID();
                String firstName = student.getFirstName();
                String lastName = student.getLastName();
                String groupName = student.getGroupName();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(studentID);
                row.createCell(1).setCellValue(firstName);
                row.createCell(2).setCellValue(lastName);
                row.createCell(3).setCellValue(groupName);
            }

            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }
        }
    }

    @Override
    public List<Student> importStudents(String fileName) throws IOException {
        List<Student> students = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(fileName))) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                String studentID = row.getCell(0).getStringCellValue();
                String firstName = row.getCell(1).getStringCellValue();
                String lastName = row.getCell(2).getStringCellValue();
                String groupName = row.getCell(3).getStringCellValue();

                Student student = new Student(firstName, lastName, studentID);
                student.setGroupName(groupName);
                students.add(student);
            }
        }

        return students;
    }
}
