package com.zhuchops.geomark;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {
    static boolean writeLayerTo(JSONObject jsonLayer, String dirPath) throws JSONException {
        Log.i("WRITE", "try to write a layer");

        String fileName = jsonLayer.getString("id") + ":" + jsonLayer.get("name");
        try (FileOutputStream fos = new FileOutputStream(new File(dirPath, fileName))) {
            String jsonString = jsonLayer.toString();
            fos.write(jsonString.getBytes());
        } catch (IOException e) {
            Log.e("WRITE", "cant write an object, " + e);
            return false;
        }
        return true;
    }

    static JSONObject readLayerFrom(String path) throws JSONException {
        JSONObject layer = null;

        Log.i("READ", "try to read a layer");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path))))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();
            layer = new JSONObject(jsonString);
            Log.i("READ", "successful reading");
        } catch (IOException e) {
            Log.e("READ", "cant read layer from file");
        }

        return layer;
    }

    static ArrayList<JSONObject> readLayersFrom(String dirPath) throws JSONException {
        ArrayList<JSONObject> layers = new ArrayList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        JSONObject layer = null;
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

    static boolean writeLayersTo(ArrayList<JSONObject> layers, String dirPath) throws JSONException {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                Log.i("DIR", "new directory created: " + directory.toString());
            } else {
                Log.w("DIR", "cant make a directory: " + directory.toString());
            }
        } else {
            directory.delete();
            return writeLayersTo(layers, dirPath);
        }
        for (JSONObject jsonLayer:
             layers) {
            FileManager.writeLayerTo(jsonLayer, dirPath);
        }

        return true;
    }
}
