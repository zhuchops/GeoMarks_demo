package com.zhuchops.geomark;

import java.util.ArrayList;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;

class Converter {

    static JsonObject toFile(GeoLayer layer) {
        JsonObject jsonLayer = null;

        HashMap<String, Object> data = layer.getData();
        jsonLayer = (JsonObject) Json.createObjectBuilder(data);

        return jsonLayer;
    }

    static JsonObject toFile(GeoMark mark) {
        JsonObject jsonObject = null;

        HashMap<String, Object> data = mark.getAllData();
        jsonObject = (JsonObject) Json.createObjectBuilder(data);

        return jsonObject;
    }

    static GeoLayer convertLayerFromFile(JsonObject jsonLayer) {
        GeoLayer layer = new GeoLayer(
                jsonLayer.getInt("id"),
                jsonLayer.getString("name"),
                jsonLayer.getString("description"),
                (ArrayList<GeoMark>) jsonLayer.get("layer")
        );

        return layer;
    }


//    static GeoMark convertMarkFromFile(JsonObject jsonObject) {
//        GeoMark mark = null;
//
//        mark = new GeoMark(
//                jsonObject.getString("x"),
//                jsonObject.getString("y"),
//                jsonObject.getInt("number"),
//                jsonObject.getString("name"),
//                jsonObject.getString("description")
//        );
//
//        return mark;
//    }
}
