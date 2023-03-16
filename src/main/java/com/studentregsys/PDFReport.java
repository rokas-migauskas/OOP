package com.studentregsys;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PDFReport {

    private GroupManager groupManager;

    private DataManager dataManager;

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void initGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public void generateReport(DataManager dataManager, LocalDate startDate, LocalDate endDate, String fileName) throws IOException {
        List<Student> students = dataManager.getAllStudents();

        PdfWriter pdfWriter = new PdfWriter(fileName);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument, PageSize.A4);

        String reportTitle = "Students Report";
        String dateRange = startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + " - " + endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        Paragraph title = new Paragraph(reportTitle)
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold();
        document.add(title);

        Paragraph dateRangeParagraph = new Paragraph(dateRange)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setItalic();
        document.add(dateRangeParagraph);

        Table table = new Table(5).useAllAvailableWidth();
        table.addCell("First Name");
        table.addCell("Last Name");
        table.addCell("Student ID");
        table.addCell("Group ID"); // Added Group ID header
        table.addCell("Attendance");

        for (Student student : students) {
            table.addCell(student.getFirstName());
            table.addCell(student.getLastName());
            table.addCell(student.getStudentID());
            table.addCell(student.getGroupName()); // Added group ID (group name) to the table

            // Retrieve and add attendance information to the table
            String attendance = dataManager.getAttendanceData(student, startDate, endDate);
            table.addCell(attendance);
        }

        document.add(table);
        document.close();
    }

}
