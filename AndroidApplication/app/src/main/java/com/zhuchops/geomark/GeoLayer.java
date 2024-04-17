package com.zhuchops.geomark;

import java.util.ArrayList;

public class GeoLayer {
    private final String id;
    private final byte[] imageData;
    private final ArrayList<GeoMark> layer;
    private final String name;
    private final String description;

    public ArrayList<GeoMark> getLayer() {
        return layer;
    }

    public GeoLayer(String id, byte[] imageData, String name, String description, ArrayList<GeoMark> layer) {
        this.id = id;
        this.imageData = imageData;
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

    public byte[] getImageData() {
        return imageData;
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

    public String getId() {
        return this.id;
    }

//    public HashMap<String, Object> getData() {
//        HashMap<String, Object> data = new HashMap<>();
//
//        data.put("id", this.id);
//        data.put("imageData", this.imageData);
//        data.put("name", this.name);
//        data.put("description", this.description);
//        data.put("layer", this.layer);
//
//        return data;
//    }
}
