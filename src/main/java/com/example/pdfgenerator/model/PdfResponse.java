package com.example.pdfgenerator.model;


public class PdfResponse {
    private String fileName;
    private String message;
    private boolean fromCache;

    public PdfResponse(String fileName, String message, boolean fromCache) {
        this.fileName = fileName;
        this.message = message;
        this.fromCache = fromCache;
    }

    // Getters and Setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isFromCache() { return fromCache; }
    public void setFromCache(boolean fromCache) { this.fromCache = fromCache; }
}