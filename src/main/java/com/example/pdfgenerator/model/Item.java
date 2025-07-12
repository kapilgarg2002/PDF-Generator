package com.example.pdfgenerator.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

public class Item {

    @NotBlank(message = "Item name is required")
    private String name;

    @NotBlank(message = "Item quantity is required")
    private String quantity;

    @NotNull(message = "Item rate is required")
    @Positive(message = "Item rate must be positive")
    private Double rate;

    @NotNull(message = "Item amount is required")
    @Positive(message = "Item amount must be positive")
    private Double amount;

    // Constructors
    public Item() {}

    public Item(String name, String quantity, Double rate, Double amount) {
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) &&
                Objects.equals(quantity, item.quantity) &&
                Objects.equals(rate, item.rate) &&
                Objects.equals(amount, item.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, rate, amount);
    }
}
