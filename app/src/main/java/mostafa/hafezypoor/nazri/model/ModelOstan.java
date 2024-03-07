package mostafa.hafezypoor.nazri.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelOstan {
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @SerializedName("states")
    private List<ModelOstanState>modelOstanStates;

    public List<ModelOstanState> getModelOstanStates() {
        return modelOstanStates;
    }
}
