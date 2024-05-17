package com.zhuchops.geomark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;

class Converter {

    static JSONObject toFile(GeoLayer layer) throws JSONException {
        JSONObject jsonLayer = new JSONObject();
        JSONArray jsonMarkArray = new JSONArray();
        jsonLayer.put("id", layer.getId());
        jsonLayer.put("name", layer.getName());
        jsonLayer.put("description", layer.getDescription());
        jsonLayer.put("imageData", Base64.getEncoder().encodeToString(layer.getImageData()));
        for (GeoMark mark:
                layer.getMarks()) {
            jsonMarkArray.put(Converter.toFile(mark));
        }
        jsonLayer.put("marks", jsonMarkArray);

        return jsonLayer;
    }

    static JSONObject toFile(GeoMark mark) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("x", mark.getX());
        jsonObject.put("y", mark.getY());
        jsonObject.put("number", mark.getNumber());
        jsonObject.put("name", mark.getName());
        jsonObject.put("description", mark.getDescription());

        return jsonObject;
    }

    static GeoLayer convertLayerFromFile(JSONObject jsonLayer) throws JSONException {
        JSONArray jsonMarksArray = jsonLayer.getJSONArray("marks");
        ArrayList<GeoMark> marks = new ArrayList<>();
        for (int i = 0; i < jsonMarksArray.length(); i++) {
            marks.add(Converter.convertMarkFromFile((JSONObject) jsonMarksArray.get(i)));
        }
        GeoLayer layer = new GeoLayer(
                jsonLayer.getString("id"),
                Base64.getDecoder().decode(jsonLayer.getString("imageData")),
                jsonLayer.getString("name"),
                jsonLayer.getString("description"),
                marks
        );

        return layer;
    }


    static GeoMark convertMarkFromFile(JSONObject jsonObject) throws JSONException {
        GeoMark mark = null;

        mark = new GeoMark(
                jsonObject.getString("x"),
                jsonObject.getString("y"),
                jsonObject.getInt("number"),
                jsonObject.getString("name"),
                jsonObject.getString("description")
        );

        return mark;
    }
}
