package com.example.smartshopping;

import android.util.Log;
import android.util.Pair;

import com.example.smartshopping.model.Beacon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class AreaPrediction {

    private Map<Pair<Integer,Integer>, Beacon> beacons;

    final char[][] beaconList = {{'-', '-', '-', '-'}, // unused
            {'A', '-', '-', '-'}, // beacon 1
            {'A', 'B', '-', '-'},
            {'B', '-', 'C', 'D'},
            {'A', 'C', '-', '-'},
            {'A', 'B', 'C', 'D'},
            {'B', 'D', '-', '-'},
            {'C', '-', '-', '-'},
            {'C', 'D', '-', '-'},
            {'D', '-', '-', '-'},
    };// beacon 9

    /////////////*custom function*///////////////
    final int[][] section={
            {0, 0, 0, 0}, // nothing
            {101, 102, 103, 104}, // section A
            {102, 103, 105, 106}, // section B
            {104, 105, 107, 108}, // section C
            {105, 106, 108, 109} //  section D
    };

    private ArrayList<Integer> getBeaconMinorsWithArea(char area) {
        ArrayList<Integer> beaconMinors = new ArrayList<Integer>();
        int temp = area-64;
        for (int i=0; i<4; i++)
            beaconMinors.add(section[temp][i]);
        return beaconMinors;
    }


    public AreaPrediction(Map<Pair<Integer, Integer>, Beacon> beacons) {
        this.beacons = beacons;
    }

    private Pair<Integer,Integer> getMinRssiBeaconKey(){
        double minRssi= Integer.MAX_VALUE;
        Pair<Integer,Integer> minKey = null;
        for(Map.Entry<Pair<Integer, Integer>, Beacon> entry : beacons.entrySet()){
            double rssi = entry.getValue().getRssi();
            if(minRssi<rssi){
                minRssi = rssi;
                minKey = entry.getKey();
            }
        }
        return minKey;
    }

    public char predictArea(){

        if(beacons.size() < 4){
            return ' ';
        }
        for(Beacon b : beacons.values()) {
            Log.d("beacons:", "beacons minor:"+b.getMinor()+" beacon rssi:"+b.getRssi());
        }
        int threshold = 3;
        char current_position = ' ';
        ArrayList<Integer> detectedMinors = new ArrayList<>();
        detectedMinors.addAll(beacons.values().stream().sorted(Comparator.reverseOrder()).limit(5).map(Beacon::getMinor).collect(Collectors.toList()));

        int temp = detectedMinors.get(0) -100;
        ArrayList<Character> areas = new ArrayList<>(Arrays.asList(
                beaconList[temp][0],
                beaconList[temp][1],
                beaconList[temp][2],
                beaconList[temp][3]));


        for(int minor : detectedMinors){
            Log.d("beacons:", "sorted minor:"+minor );
        }

        for (char area : areas) { //// area1 --> k로 바꾸기
            if(area == '-'){
                continue;
            }
            ArrayList<Integer> tmp = getBeaconMinorsWithArea(area); // arraylist for beacon in area
            ArrayList<Integer> compare = new ArrayList<>();
            compare.addAll(tmp);
            compare.removeAll(detectedMinors); // remove getbeacon value
            int accuracy = compare.size();
            if (accuracy < threshold) { //2개는 맞아야 진입
                threshold = accuracy;
                current_position=area;
            } ////// exception
        }
        return current_position;
    }
}
