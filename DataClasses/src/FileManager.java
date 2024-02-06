import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Writer {
    static boolean writeLayerTo(JsonArray jsonLayer, String path) {
        try (JsonWriter jsonWriter = Json.createWriter(new FileOutputStream(path))) {
            jsonWriter.write(jsonLayer);
        } catch (IOException e) {
            System.out.println("Не прочитался файл");
            return false;
        }
        return true;
    }
}
