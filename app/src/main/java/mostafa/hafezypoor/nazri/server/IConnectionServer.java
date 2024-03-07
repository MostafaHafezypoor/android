package mostafa.hafezypoor.nazri.server;



import java.util.List;

import mostafa.hafezypoor.nazri.model.ModelCitiesManage;
import mostafa.hafezypoor.nazri.model.ModelDonate;
import mostafa.hafezypoor.nazri.model.ModelOstan;
import mostafa.hafezypoor.nazri.model.ModelRules;
import mostafa.hafezypoor.nazri.model.ModelStatus;
import mostafa.hafezypoor.nazri.model.ModelVerifyManager;
import mostafa.hafezypoor.nazri.model.ModelVotive;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IConnectionServer {
@Headers({"token:nazri"})
@FormUrlEncoded
@POST("votive")
Call<ModelVotive> getVotive(@Field("type")String type,@Field("state")int state,@Field("city")String city);

    @Headers({"token:nazri"})
    @POST("states")
    Call<ModelOstan> getOstanState();

    @Headers({"token:nazri"})
    @FormUrlEncoded
    @POST("cities")
    Call<ModelCitiesManage> getCities(@Field("state_id")int state_id);
    @Headers({"token:nazri"})
    @FormUrlEncoded
    @POST("login")
    Call<Void> login(@Field("phone_number")String phone_number);
    @Headers({"token:nazri"})
    @FormUrlEncoded
    @POST("verify")
    Call<ModelVerifyManager> verify(@Field("phone_number")String phone_number, @Field("code")String code);

    @POST("donates")
    Call<ModelDonate> donates(@Header("Authorization")String authorization);
    @Headers({"token:nazri"})
    @FormUrlEncoded
    @POST("add_food")
    Call<ModelStatus> add_food(@Header("Authorization")String authorization, @Field("food_name")String food_name, @Field("type")String type, @Field("date_start_at")String date_start_at, @Field("date_end_at")String date_end_at, @Field("state_id")String sate_id, @Field("city_id")String city_id, @Field("location")String loction, @Field("latitude")double latitude, @Field("longitude")double longitude);
 @Headers({"token:nazri"})
    @POST("rules")
    Call<ModelRules> rules();
    @FormUrlEncoded
    @POST("add_online")
    Call<ModelStatus> add_online(@Header("Authorization")String authorization, @Field("title")String title, @Field("description")String description, @Field("site_name")String site_name, @Field("site_url")String site_url, @Field("expire_time")String expire_time);

    @FormUrlEncoded
    @POST("add_book")
    Call<ModelStatus> add_book(@Header("Authorization")String authorization, @Field("book_name")String book_name, @Field("book_type")String book_type, @Field("writer")String writer, @Field("publisher")String publisher, @Field("description")String description, @Field("state_id")String state_id, @Field("city_id")String city_id, @Field("location")String location, @Field("latitude")double latitude, @Field("longitude")double longitude);
    @FormUrlEncoded
    @POST("add_other")
    Call<ModelStatus> add_other(@Header("Authorization")String authorization, @Field("title")String title, @Field("description")String description, @Field("state_id")String state_id, @Field("city_id")String city_id, @Field("location")String location,@Field("latitude")double latitude, @Field("longitude")double longitude);


    @POST("my_votive")
    Call<ModelVotive> getMyVotive(@Header("Authorization")String authorization);

    @FormUrlEncoded
    @POST("food_del")
    Call<ModelStatus> food_del(@Header("Authorization")String authorization, @Field("votive_id")String votive_id);
    @FormUrlEncoded
    @POST("book_del")
    Call<ModelStatus> book_del(@Header("Authorization")String authorization, @Field("votive_id")String votive_id);
    @FormUrlEncoded
    @POST("other_del")
    Call<ModelStatus> other_del(@Header("Authorization")String authorization, @Field("votive_id")String votive_id);
    @FormUrlEncoded
    @POST("online_del")
    Call<ModelStatus> online_del(@Header("Authorization")String authorization, @Field("votive_id")String votive_id);
    @POST("notification")
    Call<ModelVotive> notification(@Header("Authorization")String authorization,@Field("user_notification")String user_notification,@Field("state_id")String user_id,@Field("city_id")String city_id);

    @POST("aaafffaufuudahdaf.php")
    Call<String> p();



}
