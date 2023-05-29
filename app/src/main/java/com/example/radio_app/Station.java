package com.example.radio_app;

public class Station {

    private String name;
    private String url;

    static boolean playing = false;
    static String currentStation = "";

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Station(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // ToDo Maybe use Enum?
    public static void setPlaying(){
        playing = true;
    }

    public static void setStopped(){
        playing = false;
    }

    public static void setCurrentStation() {
        currentStation = "";
    }

    public static void setCurrentStation(String url) {
        currentStation = url;
    }

}
