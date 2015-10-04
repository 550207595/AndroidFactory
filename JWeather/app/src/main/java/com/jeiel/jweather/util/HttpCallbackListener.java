package com.jeiel.jweather.util;

/**
 * Created by Jeiel on 2015/10/4.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
