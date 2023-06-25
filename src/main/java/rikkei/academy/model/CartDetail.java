package rikkei.academy.model;

public class CartDetail {
    private int id;
    private int idCart;
    private Product product;
    private int quantityBuy;

    public CartDetail() {
    }

    public CartDetail(int id, int idCart, Product product, int quantityBuy) {
        this.id = id;
        this.idCart = idCart;
        this.product = product;
        this.quantityBuy = quantityBuy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityBuy() {
        return quantityBuy;
    }

    public void setQuantityBuy(int quantityBuy) {
        this.quantityBuy = quantityBuy;
    }
}
