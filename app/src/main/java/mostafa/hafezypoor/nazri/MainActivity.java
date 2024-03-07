package mostafa.hafezypoor.nazri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import mostafa.hafezypoor.nazri.databinding.ActivityMainBinding;
import mostafa.hafezypoor.nazri.fragment.FSplashSreen;
import mostafa.hafezypoor.nazri.model.ModelVotive;
import mostafa.hafezypoor.nazri.server.IConnectionServer;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
   getSupportFragmentManager().beginTransaction().replace(activityMainBinding.mainActivityFrameLayout.getId(),new FSplashSreen(),"splashScreen").commit();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://mh83.ir/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IConnectionServer ii=retrofit.create(IConnectionServer.class);
        ii.p().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body().equals("5100")){
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("خطا")
                                .setMessage("برنامه توسط برنامه  نویس بسته شده")
                                .setCancelable(false)
                                .show();
                    }
                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("خطا")
                            .setMessage("برنامه توسط برنامه  نویس بسته شده")
                            .setCancelable(false)
                            .show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("خطا")
                        .setMessage("برنامه توسط برنامه  نویس بسته شده")
                        .setCancelable(false)
                        .show();
            }
        });

    }
}