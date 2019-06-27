package com.example.a11605.dz_project2;

public class Meeting {
    private String title;
    private String name;
    private  String start_time;
    private String ending_time;

    public Meeting(){
        title = null;
        name = null;
        start_time = null;
        ending_time = null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setEnding_time(String ending_time) {
        this.ending_time = ending_time;
    }

    public String getEnding_time() {
        return ending_time;
    }
}
