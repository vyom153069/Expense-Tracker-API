package com.vyom.expensetrackerapi.domain;

public class Transection {
    
    private Integer transectionId;
    private Integer categoryId;
    private Integer userId;
    private Double amount;
    private String note;
    private Long transectionDate;

    public Transection(Integer transectionId, Integer categoryId, Integer userId, Double amount, String note, Long transectionDate) {
        this.transectionId = transectionId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.amount = amount;
        this.note = note;
        this.transectionDate = transectionDate;
    }

    public Integer getTransectionId() {
        return this.transectionId;
    }

    public void setTransectionId(Integer transectionId) {
        this.transectionId = transectionId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getTransectionDate() {
        return this.transectionDate;
    }

    public void setTransectionDate(Long transectionDate) {
        this.transectionDate = transectionDate;
    }
    
    
}
