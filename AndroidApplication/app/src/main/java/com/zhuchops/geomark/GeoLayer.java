package com.zhuchops.geomark;

import java.util.ArrayList;

public class GeoLayer {
    private ArrayList<GeoMark> layer;
    public GeoLayer(ArrayList<GeoMark> layer) {
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

}
