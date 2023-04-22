package com.studentregsys;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataExport {

    public void exportStudents(List<Student> students, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");
            createHeaderRow(sheet);

            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(student.getFirstName());
                row.createCell(1).setCellValue(student.getLastName());
                row.createCell(2).setCellValue(student.getStudentID());
            }

            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("First Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Last Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Student ID");
        headerCell.setCellStyle(headerStyle);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    public List<Student> importStudents(String fileName) throws IOException {
        List<Student> students = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(fileName))) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String firstName = row.getCell(0).getStringCellValue();
                String lastName = row.getCell(1).getStringCellValue();
                String studentID = row.getCell(2).getStringCellValue();

                students.add(new Student(firstName, lastName, studentID));
            }
        }

        return students;
    }
}
