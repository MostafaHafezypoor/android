package mostafa.hafezypoor.nazri.model;

import com.google.gson.annotations.SerializedName;

public class ModelVotive {
    private int state;
    private String message;
    @SerializedName("votives")
    private ModelVoiveVotives modelVoiveVotives;

    public int getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public ModelVoiveVotives getModelVoiveVotives() {
        return modelVoiveVotives;
    }
}
