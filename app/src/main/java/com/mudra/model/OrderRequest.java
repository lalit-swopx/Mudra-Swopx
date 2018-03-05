package com.mudra.model;

/**
 * Created by Lenovo on 16-02-2018.
 */

public class OrderRequest {

    private String clientId;
    private String order_list;
    private String quantity;
    private String order_image;
    private String status;

    private String id;
    private String date;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder_image() {
        return order_image;
    }

    public void setOrder_image(String order_image) {
        this.order_image = order_image;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrder_list() {
        return order_list;
    }

    public void setOrder_list(String order_list) {
        this.order_list = order_list;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
