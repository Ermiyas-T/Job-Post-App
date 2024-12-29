package com.jobpost;

public class CategoryModel {
    String Title;
    String Description;
    int categoryImage;

    public CategoryModel(String Title, String Description, int categoryImage) {
        this.Title = Title;
        this.Description = Description;
        this.categoryImage =categoryImage;
    }
    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public int getCategoryImage() {
        return categoryImage;
    }

}
