package com.codecool.shop.order;

import com.codecool.shop.model.Product;

public class LineItem {
    private Product product;
    private int quantity = 1;

    public LineItem(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getLinePriceAmount() {
        return product.getDefaultPrice()*quantity;
    }

    public String getLinePrice() {
        float price = getLinePriceAmount();
        return String.valueOf(price) + " " + product.getDefaultCurrency().toString();
    }
}
