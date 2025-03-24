package com.example.recycleview;

public class Item {
    private int imageResId; // ID của hình ảnh trong drawable
    private String text;    // Nội dung text

    // Constructor để khởi tạo giá trị cho item
    public Item(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    // Getter để lấy giá trị của hình ảnh
    public int getImageResId() {
        return imageResId;
    }

    // Getter để lấy giá trị text
    public String getText() {
        return text;
    }
}
