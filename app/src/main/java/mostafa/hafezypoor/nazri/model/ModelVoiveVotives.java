package mostafa.hafezypoor.nazri.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelVoiveVotives {
    @SerializedName("foods")
    private List<ModelFoods> modelFoods;
    @SerializedName("others")
    private List<ModelOthers> modelOthers;
    @SerializedName("onlines")
   private List<ModelOnlines> modelOnlines;
    @SerializedName("books")
    private List<ModelBooks>modelBooks;

    public List<ModelBooks> getModelBooks() {
        return modelBooks;
    }

    public List<ModelFoods> getModelFoods() {
        return modelFoods;
    }

    public List<ModelOthers> getModelOthers() {
        return modelOthers;
    }

    public List<ModelOnlines> getModelOnlines() {
        return modelOnlines;
    }
}
