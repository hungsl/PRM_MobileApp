package com.example.recycleview;

public class CartItem {
    private int cartItemId;
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private String productImage;

    public CartItem(int cartItemId, int productId, String productName, double price, int quantity, String productImage) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productImage = productImage;
    }

    public CartItem(int productId, String productName, double price, int quantity) {
        this.cartItemId = 0; // Giá trị mặc định cho cartItemId
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productImage = null; // Gán giá trị mặc định cho productImage
    }
    public int getCartItemId() {
        return cartItemId;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}