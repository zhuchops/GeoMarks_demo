package com.zhuchops.geomark;

import java.util.ArrayList;

public class GeoLayer {
    private final String id;
    private final byte[] imageData;
    private final ArrayList<GeoMark> layer;
    private final String name;
    private final String description;


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

    public int getSize() {
        return layer.size();
    }

    public void changeMark(int index, GeoMark mark) {
        this.layer.set(index, mark);
    }

    public ArrayList<GeoMark> getMarks() {
        return layer;
    }

    public GeoMark getMark(int index) {
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
}
