package com.example.smartshopping.model;

import java.util.UUID;

public class Beacon {
    private UUID uuid;
    private int rssi;
    private int major;
    private int minor;
    private String name;

    public Beacon(UUID uuid, int rssi, int major, int minor, String name) {
        this.uuid = uuid;
        this.rssi = rssi;
        this.major = major;
        this.minor = minor;
        this.name = name;
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
