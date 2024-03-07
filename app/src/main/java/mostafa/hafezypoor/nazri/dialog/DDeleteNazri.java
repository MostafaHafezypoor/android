package mostafa.hafezypoor.nazri.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.fragment.FDashboard;
import mostafa.hafezypoor.nazri.model.ModelStatus;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DDeleteNazri extends Dialog {
    private SharedPreferences sharedPreferences;
    private String type;
    AppCompatActivity activity;
    public DDeleteNazri(@NonNull Context context, String type, String votiveID) {
        super(context);
        this.type = type;
        this.votiveID = votiveID;
        sharedPreferences= getContext().getSharedPreferences("save",Context.MODE_PRIVATE);
        activity= (AppCompatActivity) context;
    }

    private String votiveID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddelete_nazri);
        Button ok=findViewById(R.id.btnOk);
        Button exit=findViewById(R.id.btnExit);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("food")){
                    food();
                } else if (type.equals("book")) {
                    book();
                } else if (type.equals("online")) {
                    online();
                } else if (type.equals("other")) {
                  other();
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    private void food(){
        String token=sharedPreferences.getString("token",null);
        RetrofitInit.getInstance().food_del("Bearer "+token,votiveID).enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "حذف  شد", Toast.LENGTH_SHORT).show();

                   activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FDashboard(),"dadssadsdafadff").commit();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {

            }
        });
    }
    private void book(){
        String token=sharedPreferences.getString("token",null);
        RetrofitInit.getInstance().book_del("Bearer "+token,votiveID).enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "حذف  شد", Toast.LENGTH_SHORT).show();

                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FDashboard(),"dadssadsdafadff").commit();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {

            }
        });
    }
    private void online(){
        String token=sharedPreferences.getString("token",null);
        RetrofitInit.getInstance().online_del("Bearer "+token,votiveID).enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "حذف  شد", Toast.LENGTH_SHORT).show();

                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FDashboard(),"dadssadsdafadff").commit();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {

            }
        });
    }
    private void other(){
        String token=sharedPreferences.getString("token",null);
        RetrofitInit.getInstance().other_del("Bearer "+token,votiveID).enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "حذف  شد", Toast.LENGTH_SHORT).show();

                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FDashboard(),"dadssadsdafadff").commit();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {

            }
        });
    }
}
