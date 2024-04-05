package com.zhuchops.geomark;

import android.util.Log;

import java.util.ArrayList;

import javax.json.JsonObject;

//все доступные пользователю слои, данные с прошлого запуска
public class BoxClass {
    private static BoxClass instance;
    private final String LAYERS_DIR = "app_data/layers/";
    private ArrayList<GeoLayer> allLayers = new ArrayList<>();
    private ArrayList<String> idsOfActiveLayers = new ArrayList<>();

    private ArrayList<String> ids = new ArrayList<>();

    private BoxClass() {
        GeoLayer layer;

        for (JsonObject layerObject:
                FileManager.readLayersFrom(this.LAYERS_DIR)) {
            layer = Converter.convertLayerFromFile(layerObject);
            allLayers.add(layer);
            addIdToTable(layer.getId());
        }
    }

    public static BoxClass getInstance() {
        if (instance == null) {
            synchronized (BoxClass.class) {
                if (instance == null)
                    instance = new BoxClass();
            }
        }
        return instance;
    }

    public void addIdToActiveIds(String id) {
        idsOfActiveLayers.add(id);
    }

    public void removeIdFromActiveIds(Integer id) {
        this.idsOfActiveLayers.remove(id);
    }
    
    public void onStop() {
        ArrayList<JsonObject> jsonLayers = new ArrayList<>();

        for (GeoLayer layer:
             this.allLayers) {
            jsonLayers.add(Converter.toFile(layer));
        }
        for (int i = 1; i < 3; i++) {
            Log.i("WRITE", "trying to write layers: attempt " + i);
            if (FileManager.writeLayersTo(jsonLayers, this.LAYERS_DIR)) {
                break;
            } else {
                Log.e("WRITE", "cant to write layers");
            }
        }
        jsonLayers.clear();
    }

    public ArrayList<String> getIdsOfActiveLayers() {
        return this.idsOfActiveLayers;
    }

    public GeoLayer getLayerWithId(Integer id) {
        return this.allLayers.get(this.ids.indexOf(id));
    }

    private void addIdToTable(String id) {
        this.ids.add(id);
    }

    public void addNewLayer(GeoLayer layer) {
        allLayers.add(layer);
        addIdToTable(layer.getId());
    }
}
