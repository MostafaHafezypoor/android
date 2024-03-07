package mostafa.hafezypoor.nazri.model;

public class ModelCities {
    private int id;
    private String name;
    private String latitude;
    private String longitude;
    private int state_id;
    private String created_at;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getState_id() {
        return state_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    private String updated_at;
}
