package com.zhuchops.geomark;

import android.util.Log;

import com.yandex.mapkit.map.MapWindow;

import java.util.ArrayList;

import javax.json.JsonObject;

//все доступные пользователю слои, данные с прошлого запуска
public class BoxClass {
    private final String ACTIVE_LAYERS_DIR = "layers/active_layers_dir";
    private final String INACTIVE_LAYERS_DIR = "layers/inactive_layers_dir";
    private MapWindow mapWindow;
    private ArrayList<GeoLayer> inactiveUsersLayers = new ArrayList<>();
    private ArrayList<GeoLayer> activeUsersLayers = new ArrayList<>();

    public BoxClass(MapWindow mapWindow) {
        this.mapWindow = mapWindow;

        for (JsonObject layer:
             FileManager.readLayersFrom(this.ACTIVE_LAYERS_DIR)) {
            activeUsersLayers.add(Converter.convertLayerFromFile(layer));
        }

        for (JsonObject layer:
                FileManager.readLayersFrom(this.INACTIVE_LAYERS_DIR)) {
            inactiveUsersLayers.add(Converter.convertLayerFromFile(layer));
        }
    }

    public ArrayList<GeoLayer> getInactiveUsersLayers() {
        return inactiveUsersLayers;
    }

    public ArrayList<GeoLayer> getActiveUsersLayers() {
        return activeUsersLayers;
    }

    public MapWindow getMapWindow() {
        return this.mapWindow;
    }

    public void addLayerToActiveUsersLayers(GeoLayer layer) {
        this.activeUsersLayers.add(layer);
        this.inactiveUsersLayers.remove(layer);
    }

    public void removeLayerFromActiveLayers(GeoLayer layer) {
        this.activeUsersLayers.remove(layer);
        this.inactiveUsersLayers.add(layer);
    }
    
    public void onStop() {
        ArrayList<JsonObject> jsonLayers = new ArrayList<>();

        for (GeoLayer layer:
             this.activeUsersLayers) {
            jsonLayers.add(Converter.toFile(layer));
        }
        for (int i = 1; i < 3; i++) {
            Log.i("WRITE", "trying to write layers: attempt " + i);
            if (FileManager.writeLayersTo(jsonLayers, this.ACTIVE_LAYERS_DIR)) {
                break;
            } else {
                Log.e("WRITE", "cant to write layers");
            }
        }
        jsonLayers.clear();

        for (GeoLayer layer:
                this.inactiveUsersLayers) {
            jsonLayers.add(Converter.toFile(layer));
        }
        for (int i = 1; i < 3; i++) {
            Log.i("WRITE", "trying to write layers: attempt " + i);
            if (FileManager.writeLayersTo(jsonLayers, this.INACTIVE_LAYERS_DIR)) {
                break;
            } else {
            Log.e("WRITE", "cant to write layers");
            }
        }
    }
}
