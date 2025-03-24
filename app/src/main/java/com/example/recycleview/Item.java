package com.example.recycleview;

//public class Item {
//    private int imageResId; // ID của hình ảnh trong drawable
//    private String productName;    // Tên sản phẩm
//    private String briefDescription; // Mô tả ngắn
//    private String fullDescription; // Mô tả đầy đủ
//    private String technicalSpecifications; // Thông số kỹ thuật
//    private double price; // Giá
//
//    // Constructor
//    public Item(int imageResId, String productName, String briefDescription,
//                String fullDescription, String technicalSpecifications, double price) {
//        this.imageResId = imageResId;
//        this.productName = productName;
//        this.briefDescription = briefDescription;
//        this.fullDescription = fullDescription;
//        this.technicalSpecifications = technicalSpecifications;
//        this.price = price;
//    }
//
//    // Getters
//    public int getImageResId() { return imageResId; }
//    public String getProductName() { return productName; }
//    public String getBriefDescription() { return briefDescription; }
//    public String getFullDescription() { return fullDescription; }
//    public String getTechnicalSpecifications() { return technicalSpecifications; }
//    public double getPrice() { return price; }
//}

public class Item {
    private int productId;
    private String productName;
    private String briefDescription;
    private String fullDescription;
    private String technicalSpecifications;
    private double price;
    private String imageUrl;
    private int categoryId;
    private Category category;

    // Getters and setters for all fields

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Add Category class (nested class)
    public static class Category {
        private int categoryId;
        private String categoryName;

        // Getters and setters
        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
