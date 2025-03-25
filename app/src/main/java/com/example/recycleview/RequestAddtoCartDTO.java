package com.example.recycleview;

public class RequestAddtoCartDTO {
    private int productId;
    private int quantity;
    private String userName;

    public RequestAddtoCartDTO(int productId, int quantity, String userName) {
        this.productId = productId;
        this.quantity = quantity;
        this.userName = userName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userName;
    }

    public void setUserId(int userId) {
        this.userName = userName;
    }
}
