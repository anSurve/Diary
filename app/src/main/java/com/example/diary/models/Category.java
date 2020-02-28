package com.example.diary.models;

public class Category {
    private String Category;
    private String CategoryType;

    public Category(String category, String categoryType) {
        Category = category;
        CategoryType = categoryType;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategoryType() {
        return CategoryType;
    }

    public void setCategoryType(String categoryType) {
        CategoryType = categoryType;
    }
}
