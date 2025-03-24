package com.example.recycleview;

import com.google.gson.annotations.SerializedName;

public class ProductDetailDTO {
    @SerializedName("productId")
    private int productId;

    @SerializedName("productName")
    private String productName;

    @SerializedName("briefDescription")
    private String briefDescription;

    @SerializedName("fullDescription")
    private String fullDescription;

    @SerializedName("technicalSpecifications")
    private String technicalSpecifications;

    @SerializedName("price")
    private double price;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("categoryName")
    private String categoryName;

    // Getters and Setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getTechnicalSpecifications() {
        return technicalSpecifications;
    }

    public void setTechnicalSpecifications(String technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
