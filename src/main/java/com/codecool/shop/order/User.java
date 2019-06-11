package com.codecool.shop.order;

import java.lang.reflect.Field;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String billingCountry;
    private String billingCity;
    private String billingZip;
    private String billingAddress;
    private String shippingCountry;
    private String shippingCity;
    private String shippingZip;
    private String shippingAddress;

    public Field getDeclaredField(String param) throws NoSuchFieldException {
        return this.getClass().getDeclaredField(param);
    }

    public void setField(Field field, String value) throws IllegalAccessException {
        field.set(this, value);
    }

}
