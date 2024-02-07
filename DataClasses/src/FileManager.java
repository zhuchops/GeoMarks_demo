import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
    static boolean writeLayerTo(JsonArray jsonLayer, String path) {
        try (JsonWriter jsonWriter = Json.createWriter(new FileOutputStream(path))) {
            jsonWriter.write(jsonLayer);
        } catch (IOException e) {
            System.out.println("Не прочитался файл");
            return false;
        }
        return true;
    }

    static JsonArray readLayerFrom(String path) {
        JsonArray layer = null;

        try (JsonReader jsonReader = Json.createReader(new FileInputStream(path))) {
            layer = jsonReader.readArray();
        } catch (IOException e) {
            System.out.println("не удалось записать файл");
        }

        return layer;
    }
}
