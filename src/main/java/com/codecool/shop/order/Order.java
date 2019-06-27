package com.codecool.shop.order;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.DB.ProductDaoDB;
import com.codecool.shop.model.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    private List<LineItem> itemList = new ArrayList<>();
    private User user;
    private static Order instance = null;

    private Order() {}

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    public List<LineItem> getItems() {
        return itemList;
    }

    public void add(int id){

        ProductDao productDataStore = ProductDaoDB.getInstance();

        Product item = productDataStore.find(id);

        // getting the proper Product (by id)
        if (item.getId() == id) {
            boolean itemIsAlreadyInCart = false;

            // if the product is already in the cart, it increases the quantity of the lineitem
            for (LineItem orderItem: itemList) {
                if (orderItem.getProduct().getId() == (item.getId())) {
                    orderItem.setQuantity(orderItem.getQuantity() + 1);
                    logger.debug("Item with id {} added to the order.", id);
                    itemIsAlreadyInCart = true;
                }
            }

            // if the product isn't in the cart, it adds the lineitem to it
            if (!itemIsAlreadyInCart) {
                LineItem lineItem = new LineItem(item);
                itemList.add(lineItem);
                logger.debug("Item with id {} added to the order.", id);
            }
        }

    }

    public void remove(int id){
        for (LineItem lineItem:itemList) {
            if(lineItem.getProduct().getId()==id){
                if (lineItem.getQuantity() > 1) {
                    lineItem.setQuantity(lineItem.getQuantity() - 1);
                } else {
                    itemList.remove(lineItem);
                }
                logger.debug("One piece of product with id {} was removed.", id);
                break;
            }
        }
    }

    public String getTotalPrice() {
        float price = 0;
        for (LineItem lineItem : itemList) {
            price += lineItem.getLinePriceAmount();
        }
        return String.valueOf(price) + " USD";
    }

    public int calculateNumberOfItemsInCart() {
        int sumOfItems = 0;
        for (LineItem lineItem: itemList) {
            sumOfItems += lineItem.getQuantity();
        }
        return sumOfItems;
    }

    public void clearOrder() {
        itemList = new ArrayList<>();
        logger.info("Order cleared.");
    }

    public void setUser(User user) {
        this.user = user;
    }
}
