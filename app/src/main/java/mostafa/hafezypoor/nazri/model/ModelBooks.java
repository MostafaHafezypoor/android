package mostafa.hafezypoor.nazri.model;

public class ModelBooks {
    private int id;
    private String name;
    public String getStatus() {
        return status;
    }

    private String status;
    private String book_name;

    public String getBook_name() {
        return book_name;
    }

    public String getBook_type() {
        return book_type;
    }

    private String book_type;
    private String type;
    private String state_id;
    private String city_id;
    private String description;
    private String active_time;
    private String writer;
    private String publisher;
    private String latitude;
    private String longitude;
    private String location;

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

    public String getDescription() {
        return description;
    }

    public String getActive_time() {
        return active_time;
    }

    public String getWriter() {
        return writer;
    }

    public String getPublisher() {
        return publisher;
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

    private String date;
}
