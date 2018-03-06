package model;
import java.util.*;
public class Patient {
    private String name,appTime;

    public Patient(){}
    public Patient(String name, String appTime1) {
        this.name = name;
        this.appTime = appTime1;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getAppTime() {
        return appTime;
    }
}