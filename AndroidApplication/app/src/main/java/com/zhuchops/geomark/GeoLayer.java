package com.zhuchops.geomark;

import java.util.ArrayList;
import java.util.HashMap;

public class GeoLayer{
    private ArrayList<GeoMark> layer;
    private String name;
    private String description;

    public GeoLayer(String name, String description, ArrayList<GeoMark> layer) {
        this.name = name;
        this.description = description;
        this.layer = layer;
    }

    public void addMark(GeoMark mark) {
        this.layer.add(mark);
    }

    public void addMark(int index, GeoMark mark) {
        this.layer.add(index, mark);
    }

    public void removeMark(int index) {
        this.layer.remove(index);
    }

    public ArrayList<GeoMark> getMarks() {
        return layer;
    }

    public int size() {
        return layer.size();
    }

    public GeoMark get(int index) {
        return layer.get(index);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, Object> getData() {
        HashMap<String, Object> data = new HashMap<>();

        data.put("name", this.name);
        data.put("description", this.description);
        data.put("layer", this.layer);

        return data;
    }
}
