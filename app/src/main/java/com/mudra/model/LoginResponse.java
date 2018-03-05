package com.mudra.model;

/**
 * Created by Lenovo on 12-02-2018.
 */

public class LoginResponse {

    private BodyRes body;
    private String result;
    private String msg;
    private LoginObject client_detail;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BodyRes getBody() {
        return body;
    }

    public void setBody(BodyRes body) {
        this.body = body;
    }

    public LoginObject getClient_detail() {
        return client_detail;
    }

    public void setClient_detail(LoginObject client_detail) {
        this.client_detail = client_detail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
