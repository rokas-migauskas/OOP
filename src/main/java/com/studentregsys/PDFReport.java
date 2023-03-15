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

public class PDFReport {

    public void generateReport(List<Student> students, LocalDate startDate, LocalDate endDate, String fileName) throws IOException {
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

        Table table = new Table(3).useAllAvailableWidth();
        table.addCell("First Name");
        table.addCell("Last Name");
        table.addCell("Student ID");

        for (Student student : students) {
            table.addCell(student.getFirstName());
            table.addCell(student.getLastName());
            table.addCell(student.getStudentID());
        }

        document.add(table);
        document.close();
    }
}
