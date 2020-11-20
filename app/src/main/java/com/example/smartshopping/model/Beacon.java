package com.example.smartshopping.model;

import java.util.ArrayList;
import java.util.UUID;

public class Beacon implements Comparable<Beacon> {
    private UUID uuid;
    private double rssi;
    private int major;
    private int minor;
    private String name;
    private double max_rssi;
    private double min_rssi;
    private ArrayList<Integer> rssiList;

    public Beacon(UUID uuid, int rssi, int major, int minor, String name, ArrayList<Integer> rssiList) {
        this.uuid = uuid;
        this.rssi = rssi;
        this.major = major;
        this.minor = minor;
        this.name = name;
        max_rssi = rssi;
        min_rssi = rssi;
        this.rssiList = rssiList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ArrayList<Integer> getRssiList() {
        return rssiList;
    }

    public void setRssiList(ArrayList<Integer> rssiList) {
        this.rssiList = rssiList;
    }

    public double getRssi() {
        return rssi;
    }

    public void setRssi(double rssi) {
        if(min_rssi > rssi){
            //새로운 값이 더 작으면
            min_rssi = rssi;
        }
        if(max_rssi < rssi){
            max_rssi = rssi;
        }
        double len = (double)rssiList.size();
        double oldweight = (len-1)/len;
        double newweight = 1/len;
        this.rssi = (this.rssi * oldweight) + (rssi* newweight);
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
    public void addRssi(int rssi){
        rssiList.add(rssi);
    }


    @Override
    public int compareTo(Beacon beacon) {
        return Double.compare(this.rssi,beacon.getRssi());
    }
}
