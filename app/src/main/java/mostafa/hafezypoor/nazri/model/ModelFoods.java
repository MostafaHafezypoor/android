package mostafa.hafezypoor.nazri.model;

import static com.google.gson.internal.$Gson$Types.arrayOf;

import com.google.gson.annotations.SerializedName;

public class ModelFoods {
    private int id;

    private String name;

    public String getFood_name() {
        return food_name;
    }

    private String food_name;
    private String type;

    public String getFood_type() {
        return food_type;
    }

    private String food_type;

    public String getStatus() {
        return status;
    }

    private String status;
    private String state_id;
    private String city_id;
    private String start_at;
    private String end_at;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getState_id() {
        return state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getStart_at() {
        return start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    private String latitude;
    private String longitude;
    private String location;
    private String date;
}
