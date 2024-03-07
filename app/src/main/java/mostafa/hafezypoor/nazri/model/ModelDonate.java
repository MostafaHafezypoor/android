package mostafa.hafezypoor.nazri.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelDonate {
    private String status;
    private String message;
    @SerializedName("donates")
    private List<donate> donate;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelDonate.donate> getDonate() {
        return donate;
    }

    public  class donate{
        private int id;
        private String amount;
        private String time;

        public int getId() {
            return id;
        }

        public String getAmount() {
            return amount;
        }

        public String getTime() {
            return time;
        }
    }
}
