package rikkei.academy.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private Category category;
    private String image = "https://firebasestorage.googleapis.com/v0/b/nguyenchien-40b00.appspot.com/o/images.png?alt=media&token=55f69551-99c3-463f-b865-1975f9af59b1";
    private String title;
    private int quantity;
    private int stock;
    private float price;
    private boolean status;
    List<User> likeList = new ArrayList<>();

    public Product() {
    }

    public Product(int id, String name, Category category, String image, String title, int quantity, int stock, float price, boolean status, List<User> likeList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.image = image;
        this.title = title;
        this.quantity = quantity;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.likeList = likeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<User> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<User> likeList) {
        this.likeList = likeList;
    }
}
