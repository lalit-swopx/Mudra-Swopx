package com.mudra.model;

import java.util.ArrayList;

/**
 * Created by Lenovo on 21-02-2018.
 */

public class OrderListResponse {
    private String msg;
    private String result;
    private ArrayList<OrderRequest> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<OrderRequest> getList() {
        return list;
    }

    public void setList(ArrayList<OrderRequest> list) {
        this.list = list;
    }
}
