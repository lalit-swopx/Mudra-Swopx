package com.mudra.model;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15-02-2018.
 */

public class ProductListResponse {

    private String msg;
    private String result;
    private ArrayList<ProductListObject> list;

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

    public ArrayList<ProductListObject> getList() {
        return list;
    }

    public void setList(ArrayList<ProductListObject> list) {
        this.list = list;
    }
}
