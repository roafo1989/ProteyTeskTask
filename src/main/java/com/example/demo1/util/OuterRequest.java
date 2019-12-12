package com.example.demo1.util;

import java.util.concurrent.TimeUnit;

public class OuterRequest {

    public static void sendOuterRequest(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
