package com.example.musicianslist.models;

/**
 * Created by Murager on 17.04.2016.
 */
public class Cover {

    private String big;

    private String small;

    public Cover() {
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "big='" + big + '\'' +
                ", small='" + small + '\'' +
                '}';
    }
}
