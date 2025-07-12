package com.example.pdfgenerator.service;


import com.example.pdfgenerator.model.InvoiceRequest;
import com.example.pdfgenerator.model.PdfResponse;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {

    private final TemplateEngine templateEngine;
    private final String PDF_DIRECTORY = "pdfs";

    @Autowired
    public PdfGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        createPdfDirectory();
    }

    public PdfResponse generatePdf(InvoiceRequest request) throws Exception {
        String fileName = generateFileName(request);
        Path filePath = Paths.get(PDF_DIRECTORY, fileName);

        // Check if PDF already exists
        if (Files.exists(filePath)) {
            return new PdfResponse(fileName, "PDF retrieved from cache", true);
        }

        // Generate new PDF
        String htmlContent = generateHtmlContent(request);
        generatePdfFromHtml(htmlContent, filePath.toString());

        return new PdfResponse(fileName, "PDF generated successfully", false);
    }

    public Resource downloadPdf(String fileName) throws IOException {
        Path filePath = Paths.get(PDF_DIRECTORY, fileName);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("PDF file not found: " + fileName);
        }

        return new org.springframework.core.io.PathResource(filePath);
    }

    private String generateFileName(InvoiceRequest request) throws NoSuchAlgorithmException {
        // Generate hash based on request content for unique filename
        String content = request.toString();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(content.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return "invoice_" + hexString.toString() + ".pdf";
    }

    private String generateHtmlContent(InvoiceRequest request) {
        Context context = new Context();
        context.setVariable("seller", request.getSeller());
        context.setVariable("sellerGstin", request.getSellerGstin());
        context.setVariable("sellerAddress", request.getSellerAddress());
        context.setVariable("buyer", request.getBuyer());
        context.setVariable("buyerGstin", request.getBuyerGstin());
        context.setVariable("buyerAddress", request.getBuyerAddress());
        context.setVariable("items", request.getItems());
        context.setVariable("totalAmount", request.getTotalAmount());
        context.setVariable("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return templateEngine.process("invoice-template", context);
    }

    private void generatePdfFromHtml(String htmlContent, String outputPath) throws DocumentException, IOException {
        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        }
    }

    private void createPdfDirectory() {
        try {
            Files.createDirectories(Paths.get(PDF_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create PDF directory", e);
        }
    }
}