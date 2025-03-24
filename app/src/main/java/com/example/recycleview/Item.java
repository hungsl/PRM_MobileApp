package com.example.recycleview;

public class Item {
    private int imageResId; // ID của hình ảnh trong drawable
    private String productName;    // Tên sản phẩm
    private String briefDescription; // Mô tả ngắn
    private String fullDescription; // Mô tả đầy đủ
    private String technicalSpecifications; // Thông số kỹ thuật
    private double price; // Giá

    // Constructor
    public Item(int imageResId, String productName, String briefDescription,
                String fullDescription, String technicalSpecifications, double price) {
        this.imageResId = imageResId;
        this.productName = productName;
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.technicalSpecifications = technicalSpecifications;
        this.price = price;
    }

    // Getters
    public int getImageResId() { return imageResId; }
    public String getProductName() { return productName; }
    public String getBriefDescription() { return briefDescription; }
    public String getFullDescription() { return fullDescription; }
    public String getTechnicalSpecifications() { return technicalSpecifications; }
    public double getPrice() { return price; }
}