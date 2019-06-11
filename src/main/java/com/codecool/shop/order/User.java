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

    public Field getDeclaredField(String fieldName){
        try {
            return this.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setField(Field field, String value){
        try {
            field.set(this, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
