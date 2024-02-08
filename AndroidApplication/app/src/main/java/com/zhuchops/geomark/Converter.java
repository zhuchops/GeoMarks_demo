package com.zhuchops.geomark;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Objects;

class Converter {
    static JsonArray toFile(GeoLayer layer) {
        JsonArray jsonArray = (JsonArray) Json.createArrayBuilder();

        for (GeoMark mark:
             layer.getMarks()) {

            jsonArray.add(Converter.toFile(mark));
        }

        return jsonArray;
    }

    static JsonObject toFile(GeoMark mark) {
        JsonObject jsonObject = null;

        HashMap<String, Object> data = mark.getAllData();
        jsonObject = (JsonObject) Json.createObjectBuilder(data);

        return jsonObject;
    }

    static GeoLayer fromFile(JsonArray jsonArray) {
        GeoLayer layer = null;

        GeoMark mark;
        for (int i = 0; i < jsonArray.size(); i++) {
            mark = fromFile(jsonArray.getJsonObject(i));
            layer.addMark(mark);
        }

        return layer;
    }

    static GeoMark fromFile(JsonObject jsonObject) {
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
