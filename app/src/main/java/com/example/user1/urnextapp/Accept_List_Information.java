package com.example.user1.urnextapp;

// class to store patient information for accept page
public class Accept_List_Information {


    private String name;
    private String time;

    public Accept_List_Information(){
        //this constructor is required
    }

    public Accept_List_Information(String name, String time) {
        this.name = name;
        this.time = time;
    }

    String getname() {
        return name;
    }

    String gettime() {
        return time;
    }

    public String settime(String time) {
        return time;
    }
}

