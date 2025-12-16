package com.visaflow.form.service;

import lombok.SneakyThrows;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class FormService {

    @SneakyThrows
    public byte[] generatePdf(Map<String, Object> data) {
        // In a real app, load a template from MinIO or classpath
        // For MVP, we create a new PDF or load a dummy one if it existed.
        // Here we demonstrate creating a simple PDF with text since we don't have a template file.

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("VisaFlow Generated Application");
                contentStream.endText();

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                
                int y = 650;
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, y);
                    contentStream.showText(entry.getKey() + ": " + entry.getValue());
                    contentStream.endText();
                    y -= 20;
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }

    // Example of how filling a form would look if we had a template
    @SneakyThrows
    public byte[] fillFormTemplate(File templateFile, Map<String, String> fieldValues) {
        try (PDDocument document = Loader.loadPDF(templateFile)) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

            if (acroForm != null) {
                for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
                    PDField field = acroForm.getField(entry.getKey());
                    if (field != null) {
                        field.setValue(entry.getValue());
                    }
                }
            }
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }
}
