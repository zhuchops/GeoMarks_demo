import javax.json.JsonArrayBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GeoMark {
    private int number;
    private String name;
    private String description;
    private String x, y;

    public GeoMark(String x, String y, int number, String name, String description) {
        this.x = x; this.y = y;
        this.number = number;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public HashMap<String, Object> getAllData() {
        HashMap<String, Object> myData = new HashMap<>();
        myData.put("x", this.x);
        myData.put("y", this.y);
        myData.put("number", this.getNumber());
        myData.put("name", this.getName());
        myData.put("description", this.getDescription());
        return myData;
    }
}
