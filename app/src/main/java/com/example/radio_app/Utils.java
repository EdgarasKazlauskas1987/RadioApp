package com.example.radio_app;

public class Utils {

    public static int fromDPtoPX(float scale, int dps) {

        int pixels = (int) (dps * scale + 0.5f);

        return pixels;
    }
}
