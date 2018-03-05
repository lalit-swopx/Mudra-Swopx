package com.mudra.utils;

/**
 * Created by Lalit on 27-02-2017.
 */

public class FragmentActivityMessage {
    private String message;
    private String from;


    public FragmentActivityMessage(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

}



