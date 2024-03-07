package mostafa.hafezypoor.nazri.model;

public class ModelVerifyManager {
    private String status;
    private String message;
    private User user;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public class User{
    private int id;
    private String phone_number;
    private String ip;
    private String token;

        public int getId() {
            return id;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public String getIp() {
            return ip;
        }

        public String getToken() {
            return token;
        }
    }

}
