package com.zhuchops.geomark;

import com.yandex.mapkit.mapview.MapView;

public class MapViewHolder {
    private static MapView instance;

    public static MapView getInstance(MapView mapView) {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = mapView;
                }
            }
        }
        return instance;
    }
}