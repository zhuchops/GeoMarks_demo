package com.zhuchops.geomark;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

public class FileManager {
    static boolean writeLayerTo(JsonObject jsonLayer, String path) {
        Log.i("WRITE", "try to write a layer");
        try (JsonWriter jsonWriter = Json.createWriter(new FileOutputStream(path))) {
            jsonWriter.write(jsonLayer);
        } catch (IOException e) {
            Log.e("WRITE", "cant write an object");
            return false;
        }
        return true;
    }

    static JsonObject readLayerFrom(String path) {
        JsonObject layer = null;

        Log.i("READ", "try to read a layer");
        try (JsonReader jsonReader = Json.createReader(new FileInputStream(path))) {
            layer = jsonReader.readObject();
            Log.i("READ", "successful reading");
        } catch (IOException e) {
            Log.e("READ", "cant read layer from file");
        }

        return layer;
    }

    static ArrayList<JsonObject> readLayersFrom(String dirPath) {
        ArrayList<JsonObject> layers = new ArrayList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        JsonObject layer = null;
        Log.i("READ", "try to read a directory");
        if (files != null) {
            Log.i("READ", "successful reading");
            for (File file:
                 files) {
                layer = FileManager.readLayerFrom(file.getPath());
                Log.i("READ", "try to read a layer");
                if (layer != null) {
                    Log.i("READ", "successful reading");
                    layers.add(layer);
                } else Log.w("READ", "layer was not found");
            }
        } else Log.w("READ", "directory was not found");

        return layers;
    }

    static boolean writeLayersTo(ArrayList<JsonObject> layers, String dirPath) {
        for (JsonObject jsonLayer:
             layers) {
            String path = dirPath + "/" + jsonLayer.getString("name");
            FileManager.writeLayerTo(jsonLayer, path);
        }

        return true;
    }
}
