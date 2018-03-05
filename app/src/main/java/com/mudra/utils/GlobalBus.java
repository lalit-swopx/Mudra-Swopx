package com.mudra.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lalit on 01-02-2017.
 */

public class GlobalBus {

    private static EventBus sBus;

    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }

}
