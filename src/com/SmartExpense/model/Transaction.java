package com.SmartExpense.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private long id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private String category;
    private LocalDate date;

    public Transaction(long id, String description, BigDecimal amount, TransactionType type, String category, LocalDate date){
        if(id <=0){
           throw new IllegalArgumentException("Transaction ID must be positive");
        }

        if(description == null || description.isBlank()){
            throw new IllegalArgumentException("Description Cannot be Empty");
        }

        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if(type == null){
            throw new IllegalArgumentException("Transaction type cannot be null");
        }

        if(category == null || category.isBlank()){
            throw new IllegalArgumentException("Category Cannot be empty");
        }

        if(date == null){
            throw new IllegalArgumentException("Transaction Date cannot be null");
        }

        this.id = id;
        this.amount = amount;
        this.description= description.trim();
        this.category= category.trim();
        this.type = type;
        this.date = date ;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setAmount(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        this.amount = amount;
    }

    public void setCategory(String category) {
        if(category == null || category.isBlank()){
            throw new IllegalArgumentException("Category cannot be Empty");
        }
        this.category = category.trim();
    }

    public void setDate(LocalDate date) {
        if(date == null){
            throw new IllegalArgumentException("Transaction date cannot be null");
        }
        this.date = date;
    }

    public void setDescription(String description) {
        if(description == null || description.isBlank()){
            throw new IllegalArgumentException("Description cannot be Empty");
        }
        this.description = description.trim();
    }

    public void setType(TransactionType type) {
        if(type == null){
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        this.type = type;
    }
}
