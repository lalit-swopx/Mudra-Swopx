package com.mudra.model;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15-02-2018.
 */

public class DashboardResponse {

    private String msg;
    private String status;
    private ArrayList<DashboardObject> list;
    private ArrayList<AdvertiseObject> ad;

    public ArrayList<AdvertiseObject> getAd() {
        return ad;
    }

    public void setAd(ArrayList<AdvertiseObject> ad) {
        this.ad = ad;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<DashboardObject> getList() {
        return list;
    }

    public void setList(ArrayList<DashboardObject> list) {
        this.list = list;
    }
}
