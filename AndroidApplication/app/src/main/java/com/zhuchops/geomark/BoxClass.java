package com.zhuchops.geomark;

import com.yandex.mapkit.geometry.Geo;

import java.util.ArrayList;

//все доступные пользователю слои, данные с прошлого запуска
public class BoxClass {
    private ArrayList<GeoLayer> allUsersLayers;
    private ArrayList<GeoLayer> activeUsersLayers;
    private ArrayList<GeoLayer> inactiveUsersLayers;

    public ArrayList<GeoLayer> getAllUsersLayers() {
        return allUsersLayers;
    }

    public ArrayList<GeoLayer> getActiveUsersLayers() {
        return activeUsersLayers;
    }

    public ArrayList<GeoLayer> getInactiveUsersLayers() {
        return inactiveUsersLayers;
    }

    public void addLayerToActiveUsersLayers(GeoLayer layer) {
        
    }
}
