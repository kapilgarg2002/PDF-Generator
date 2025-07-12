package com.example.pdfgenerator.model;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceRequestTest {

    @Test
    void getTotalAmount_SingleItem_ReturnsCorrectAmount() {
        // Given
        Item item = new Item("Product 1", "12 Nos", 123.00, 1476.00);
        InvoiceRequest request = new InvoiceRequest(
                "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                Collections.singletonList(item)
        );

        // When
        double total = request.getTotalAmount();

        // Then
        assertEquals(1476.00, total, 0.01);
    }

    @Test
    void getTotalAmount_MultipleItems_ReturnsCorrectSum() {
        // Given
        Item item1 = new Item("Product 1", "10 Nos", 100.0, 1000.0);
        Item item2 = new Item("Product 2", "5 Nos", 200.0, 1000.0);
        InvoiceRequest request = new InvoiceRequest(
                "Seller", "GSTIN1", "Address1",
                "Buyer", "GSTIN2", "Address2",
                Arrays.asList(item1, item2)
        );

        // When
        double total = request.getTotalAmount();

        // Then
        assertEquals(2000.0, total, 0.01);
    }

    @Test
    void equals_SameContent_ReturnsTrue() {
        // Given
        Item item = new Item("Product 1", "12 Nos", 123.00, 1476.00);
        InvoiceRequest request1 = new InvoiceRequest(
                "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                Collections.singletonList(item)
        );
        InvoiceRequest request2 = new InvoiceRequest(
                "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                Collections.singletonList(item)
        );

        // When & Then
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void equals_DifferentContent_ReturnsFalse() {
        // Given
        Item item1 = new Item("Product 1", "12 Nos", 123.00, 1476.00);
        Item item2 = new Item("Product 2", "10 Nos", 100.00, 1000.00);
        InvoiceRequest request1 = new InvoiceRequest(
                "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                Collections.singletonList(item1)
        );
        InvoiceRequest request2 = new InvoiceRequest(
                "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                Collections.singletonList(item2)
        );

        // When & Then
        assertNotEquals(request1, request2);
    }
}
