package com.codecool.shop.order;

public class User {
    private String name;
    private String email;
    private int phoneNumber;
    private String billingCountry;
    private String billingCity;
    private int billingZip;
    private String billingAddress;
    private String shippingCountry;
    private String shippingCity;
    private int shippingZip;
    private String shippingAddress;

    public void setAttribute(String attributeName, String value) {
        switch (attributeName) {
            case "name":
                this.name = value;
                break;
            case "email":
                this.email = value;
                break;
            case "billingCountry":
                this.billingCountry = value;
                break;
            case "billingCity":
                this.billingCity = value;
                break;
            case "billingAddress":
                this.billingAddress = value;
                break;
            case "shippingCountry":
                this.shippingCountry = value;
                break;
            case "shippingCity":
                this.shippingCity = value;
                break;
            case "shippingAddress":
                this.shippingAddress = value;
                break;
            case "phoneNumber":
                this.phoneNumber = Integer.parseInt(value);
                break;
            case "billingZip":
                this.billingZip = Integer.parseInt(value);
                break;
            case "shippingZip":
                this.shippingZip = Integer.parseInt(value);
                break;
        }
    }

}
