package com.example.diary.models;

import java.util.Date;

public class Expenditure {
    private Date Date;
    private int MoneySpent;
    private String Category;
    private String Description;

    public Expenditure(Date date, int moneySpent, String category, String description) {
        Date = date;
        MoneySpent = moneySpent;
        Category = category;
        Description = description;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public int getMoneySpent() {
        return MoneySpent;
    }

    public void setMoneySpent(int moneySpent) {
        MoneySpent = moneySpent;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
