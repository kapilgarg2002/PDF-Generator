package com.example.pdfgenerator.controller;


import com.example.pdfgenerator.model.InvoiceRequest;
import com.example.pdfgenerator.model.Item;
import com.example.pdfgenerator.model.PdfResponse;
import com.example.pdfgenerator.service.PdfGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(PdfController.class)
@SpringBootTest
class PdfControllerTest {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;


    @Test
    void generatePdf_ValidRequest_ReturnsSuccess() throws Exception {
        // Given
        InvoiceRequest request = createValidInvoiceRequest();

        PdfResponse result = pdfGeneratorService.generatePdf(request);

        assertNotNull(result);
        assertNotNull(result.getFileName());
        assertTrue(result.getFileName().startsWith("invoice_"));
        assertTrue(result.getFileName().endsWith(".pdf"));
        assertFalse(result.isFromCache());
    }

    @Test
    void downloadPdf_NonExistentFile_ThrowsException() {
        // Given
        String fileName = "nonexistent.pdf";

        // When & Then
        assertThrows(FileNotFoundException.class, () -> {
            pdfGeneratorService.downloadPdf(fileName);
        });
    }

    @Test
    void invoiceRequest_CalculateTotalAmount_ReturnsCorrectSum() {
        // Given
        Item item1 = new Item("Product 1", "10 Nos", 100.0, 1000.0);
        Item item2 = new Item("Product 2", "5 Nos", 200.0, 1000.0);
        InvoiceRequest request = new InvoiceRequest(
                "Seller", "GSTIN1", "Address1",
                "Buyer", "GSTIN2", "Address2",
                java.util.Arrays.asList(item1, item2)
        );

        // When
        double total = request.getTotalAmount();

        // Then
        assertEquals(2000.0, total, 0.01);
    }

    private InvoiceRequest createValidInvoiceRequest() {
        Item item = new Item("Product 1", "12 Nos", 123.00, 1476.00);

        return new InvoiceRequest(
                "XYZ Pvt. Ltd.",
                "29AABBCCDD121ZD",
                "New Delhi, India",
                "Vedant Computers",
                "29AABBCCDD131ZD",
                "New Delhi, India",
                Collections.singletonList(item)
        );
    }
}