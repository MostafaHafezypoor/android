package mostafa.hafezypoor.nazri.server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {
    private  static IConnectionServer iConnectionServer;
    private static Retrofit retrofit;
    public static IConnectionServer getInstance(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl("https://nazri.ir/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(IConnectionServer.class);
    }

}
