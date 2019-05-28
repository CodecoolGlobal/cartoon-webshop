package com.codecool.shop.order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<LineItem> items = new ArrayList<>();
    private static Order instance = null;


    private Order() {}

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    public List<LineItem> getItems() {
        return items;
    }

}
