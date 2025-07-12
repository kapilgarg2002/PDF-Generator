package com.example.pdfgenerator.model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Objects;

public class InvoiceRequest {

    @NotBlank(message = "Seller name is required")
    private String seller;

    @NotBlank(message = "Seller GSTIN is required")
    private String sellerGstin;

    @NotBlank(message = "Seller address is required")
    private String sellerAddress;

    @NotBlank(message = "Buyer name is required")
    private String buyer;

    @NotBlank(message = "Buyer GSTIN is required")
    private String buyerGstin;

    @NotBlank(message = "Buyer address is required")
    private String buyerAddress;

    @NotEmpty(message = "Items list cannot be empty")
    @Valid
    private List<Item> items;

    // Constructors
    public InvoiceRequest() {}

    public InvoiceRequest(String seller, String sellerGstin, String sellerAddress,
                          String buyer, String buyerGstin, String buyerAddress,
                          List<Item> items) {
        this.seller = seller;
        this.sellerGstin = sellerGstin;
        this.sellerAddress = sellerAddress;
        this.buyer = buyer;
        this.buyerGstin = buyerGstin;
        this.buyerAddress = buyerAddress;
        this.items = items;
    }

    // Getters and Setters
    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public String getSellerGstin() { return sellerGstin; }
    public void setSellerGstin(String sellerGstin) { this.sellerGstin = sellerGstin; }

    public String getSellerAddress() { return sellerAddress; }
    public void setSellerAddress(String sellerAddress) { this.sellerAddress = sellerAddress; }

    public String getBuyer() { return buyer; }
    public void setBuyer(String buyer) { this.buyer = buyer; }

    public String getBuyerGstin() { return buyerGstin; }
    public void setBuyerGstin(String buyerGstin) { this.buyerGstin = buyerGstin; }

    public String getBuyerAddress() { return buyerAddress; }
    public void setBuyerAddress(String buyerAddress) { this.buyerAddress = buyerAddress; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    // Calculate total amount
    public double getTotalAmount() {
        return items.stream().mapToDouble(Item::getAmount).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceRequest that = (InvoiceRequest) o;
        return Objects.equals(seller, that.seller) &&
                Objects.equals(sellerGstin, that.sellerGstin) &&
                Objects.equals(sellerAddress, that.sellerAddress) &&
                Objects.equals(buyer, that.buyer) &&
                Objects.equals(buyerGstin, that.buyerGstin) &&
                Objects.equals(buyerAddress, that.buyerAddress) &&
                Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seller, sellerGstin, sellerAddress, buyer, buyerGstin, buyerAddress, items);
    }
}