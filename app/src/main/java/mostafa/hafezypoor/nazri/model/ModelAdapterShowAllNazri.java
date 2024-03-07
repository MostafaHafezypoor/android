package mostafa.hafezypoor.nazri.model;

import com.google.gson.annotations.SerializedName;

public class ModelAdapterShowAllNazri {
    private String id;

    public String getId() {
        return id;
    }

    private String typeVotive;
    private String type;
    private String name;
private String description;
private String location;
private String latitude;
private String longitude;
    private String date;
    private String writer;
    private String site;
    private String start_end;
    private String status;


    public ModelAdapterShowAllNazri(String id, String typeVotive, String type, String name, String description, String location, String latitude, String longitude, String date, String writer, String site, String start_end, String status) {
        this.id = id;
        this.typeVotive = typeVotive;
        this.type = type;
        this.name = name;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.writer = writer;
        this.site = site;
        this.start_end = start_end;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getStart_end() {
        return start_end;
    }

    public ModelAdapterShowAllNazri(String typeVotive, String type, String name, String description, String location, String latitude, String longitude, String date, String writer, String site, String start_end) {
        this.typeVotive = typeVotive;
        this.type = type;
        this.name = name;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.writer = writer;
        this.site = site;
        this.start_end = start_end;
    }

    public String getTypeVotive() {
        return typeVotive;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    public String getWriter() {
        return writer;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
