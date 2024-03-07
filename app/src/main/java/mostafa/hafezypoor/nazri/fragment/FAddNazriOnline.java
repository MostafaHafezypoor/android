package mostafa.hafezypoor.nazri.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.IOException;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.FaddNazriOnlineBinding;
import mostafa.hafezypoor.nazri.model.ModelStatus;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAddNazriOnline extends Fragment {
    private FaddNazriOnlineBinding faddNazriOnlineBinding;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       faddNazriOnlineBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.fadd_nazri_online,container,false);
        return faddNazriOnlineBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        faddNazriOnlineBinding.textRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FRules(),"FRules").commit();
            }
        });
        faddNazriOnlineBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMain").commit();
            }
        });
        faddNazriOnlineBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValues()){
                    String date=faddNazriOnlineBinding.dateEnd.getSelectedYear()+"-"+faddNazriOnlineBinding.dateEnd.getSelectedMonth()+"-"+faddNazriOnlineBinding.dateEnd.getSelectedDay();
                    RetrofitInit.getInstance().add_online("Bearer "+sharedPreferences.getString("token",null),faddNazriOnlineBinding.titleName.getText().toString(),faddNazriOnlineBinding.description.getText().toString(),faddNazriOnlineBinding.siteName.getText().toString(),faddNazriOnlineBinding.addressSite.getText().toString(),date).enqueue(new Callback<ModelStatus>() {
                        @Override
                        public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getContext(), "نذر شما ثبت شد", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"Fssss").commit();
                            }else{
                                if (response.code()==400){
                                    try {
                                        Gson gson=new Gson();
                                        ModelStatus modelStatus=gson.fromJson(response.errorBody().string(), ModelStatus.class);
                                      if (modelStatus.getMessage().equals("You cannot create a votive for the past tense")){
                                          Toast.makeText(getContext(), "تاریخ نذر نمیتواند از تاریخ گذشته  باشد", Toast.LENGTH_SHORT).show();
                                      }
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<ModelStatus> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
    private boolean checkValues(){
        if (faddNazriOnlineBinding.addressSite.getText().toString().trim().isEmpty()){
            faddNazriOnlineBinding.addressSite.setError("آدرس سایت نباید خالی باشد");
            return false;
        } else if (faddNazriOnlineBinding.description.getText().toString().trim().isEmpty()) {
            faddNazriOnlineBinding.description.setError("توضیحات نباید خالی باشد");
            return false;
        } else if (faddNazriOnlineBinding.titleName.getText().toString().isEmpty()) {
            faddNazriOnlineBinding.titleName.setError("عنوان نذر نباید خالی باشد");
            return false;
        }else if (faddNazriOnlineBinding.siteName.getText().toString().isEmpty()){
            faddNazriOnlineBinding.siteName.setError("نام سایت  نباید خالی باشد");
            return false;
        }

        return true;
    }
}
