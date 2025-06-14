package com.example.finalyearproject;

public class ReportModel {
    private String id;
    private String amount;
    private String date;

    public ReportModel(String id, String amount, String date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}




