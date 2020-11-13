package com.example.smartshopping.model;

import java.util.UUID;

public class Beacon {
    private UUID uuid;
    private int rssi;
    private int major;
    private int minor;
    private String name;
    private int max_rssi;
    private int min_rssi;

    public Beacon(UUID uuid, int rssi, int major, int minor, String name) {
        this.uuid = uuid;
        this.rssi = rssi;
        this.major = major;
        this.minor = minor;
        this.name = name;
        max_rssi = rssi;
        min_rssi = rssi;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }



    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        if(min_rssi > rssi){
            //새로운 값이 더 작으면
            min_rssi = rssi;
        }
        if(max_rssi < rssi){
            max_rssi = rssi;
        }
        this.rssi = rssi;

    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
