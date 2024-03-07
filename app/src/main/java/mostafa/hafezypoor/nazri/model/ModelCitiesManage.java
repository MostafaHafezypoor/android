package mostafa.hafezypoor.nazri.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCitiesManage {
    private String status;
    private String message;
    @SerializedName("cities")
    private List<ModelCities>modelCitiesList;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelCities> getModelCitiesList() {
        return modelCitiesList;
    }
}
