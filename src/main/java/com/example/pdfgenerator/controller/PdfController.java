package com.example.pdfgenerator.controller;


import com.example.pdfgenerator.model.InvoiceRequest;
import com.example.pdfgenerator.model.PdfResponse;
import com.example.pdfgenerator.service.PdfGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@Tag(name = "PDF Generator", description = "APIs for generating and downloading PDF invoices")
public class PdfController {

    private final PdfGeneratorService pdfGeneratorService;

    @Autowired
    public PdfController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate PDF invoice", description = "Generate a PDF invoice based on provided data")
    public ResponseEntity<PdfResponse> generatePdf(@Valid @RequestBody InvoiceRequest request) {
        try {
            PdfResponse response = pdfGeneratorService.generatePdf(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new PdfResponse(null, "Error generating PDF: " + e.getMessage(), false));
        }
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "Download PDF", description = "Download a generated PDF file")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName) {
        try {
            Resource resource = pdfGeneratorService.downloadPdf(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}