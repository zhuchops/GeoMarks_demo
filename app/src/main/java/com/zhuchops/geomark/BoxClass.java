package com.zhuchops.geomark;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//все доступные пользователю слои, данные с прошлого запуска
public class BoxClass {
    private static BoxClass instance;
    private static Context context;
    private final String LAYERS_DIR = "app_data/layers/";
    private ArrayList<GeoLayer> allLayers = new ArrayList<>();
    private ArrayList<String> idsOfActiveLayers = new ArrayList<>();

    private ArrayList<String> ids = new ArrayList<>();

    private BoxClass(Context context) throws JSONException {
        GeoLayer layer;
        this.context = context;
        for (JSONObject layerObject:
                FileManager.readLayersFrom(context.getFilesDir() + this.LAYERS_DIR)) {
            layer = Converter.convertLayerFromFile(layerObject);
            allLayers.add(layer);
            addIdToTable(layer.getId());
        }
    }

    public static BoxClass getInstance(Context context) throws JSONException {
        if (instance == null) {
            synchronized (BoxClass.class) {
                if (instance == null)
                    instance = new BoxClass(context);
            }
        }
        return instance;
    }

    public static void setContext(Context context) {
        BoxClass.context = context;
    }

    public void addIdToActiveIds(String id) {
        idsOfActiveLayers.add(id);
    }

    public void removeIdFromActiveIds(Integer id) {
        this.idsOfActiveLayers.remove(id);
    }
    
    public void onStop() throws JSONException {
        ArrayList<JSONObject> jsonLayers = new ArrayList<>();

        for (GeoLayer layer:
             this.allLayers) {
            jsonLayers.add(Converter.toFile(layer));
        }
        for (int i = 1; i < 3; i++) {
            Log.i("WRITE", "trying to write layers: attempt " + i);
            if (FileManager.writeLayersTo(jsonLayers, context.getFilesDir() + this.LAYERS_DIR)) {
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

    public GeoLayer getLayerWithId(String id) {
        return this.allLayers.get(this.ids.indexOf(id));
    }

    private void addIdToTable(String id) {
        this.ids.add(id);
    }

    public void addNewLayer(GeoLayer layer) {
        allLayers.add(layer);
        addIdToTable(layer.getId());
    }

    public ArrayList<String> getIds() {
        return this.ids;
    }

    public void changeGeoLayer(String id, GeoLayer newLayer) {
        allLayers.set(ids.indexOf(id), newLayer);
    }

    public void deleteLayer(GeoLayer layer) {
        int id = ids.indexOf(layer.getId());
        allLayers.remove(id);
        ids.remove(id);
    }
}
