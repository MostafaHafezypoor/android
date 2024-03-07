package mostafa.hafezypoor.nazri.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.FnotificationBinding;
import mostafa.hafezypoor.nazri.model.ModelCitiesManage;
import mostafa.hafezypoor.nazri.model.ModelOstan;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FNotification extends Fragment {
    private FnotificationBinding  fnotificationBinding;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fnotificationBinding= DataBindingUtil.inflate(getLayoutInflater(),R.layout.fnotification,container,false);
        return fnotificationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOstan();
        fnotificationBinding.sonoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sharedPreferences.edit().putBoolean("on_notificatin",true).apply();
                }else {
                    sharedPreferences.edit().putBoolean("on_notificatin",false).apply();

                }
            }
        });
        fnotificationBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FDashboard(),"sdddssd").commit();
            }
        });
        fnotificationBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMain").commit();
            }
        });
    }
    private void setOstan() {
        RetrofitInit.getInstance().getOstanState().enqueue(new Callback<ModelOstan>() {
            @Override
            public void onResponse(Call<ModelOstan> call, Response<ModelOstan> response) {
                String[] data = new String[response.body().getModelOstanStates().size()];
                for (int i = 0; i < data.length; i++) {
                    data[i] = response.body().getModelOstanStates().get(i).getName();
                }
                ArrayAdapter<String> arrayAdapterOstan = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, data);
                fnotificationBinding.spinnerOstan.setAdapter(arrayAdapterOstan);
                fnotificationBinding.spinnerOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sharedPreferences.edit().putString("state_id_notificatin",(i+1)+"").apply();
                        RetrofitInit.getInstance().getCities(response.body().getModelOstanStates().get(i).getId()).enqueue(new Callback<ModelCitiesManage>() {
                            @Override
                            public void onResponse(Call<ModelCitiesManage> call, Response<ModelCitiesManage> response) {
                                if (response.isSuccessful()) {
                                    String[] data = new String[response.body().getModelCitiesList().size()];
                                    String[] cityID = new String[response.body().getModelCitiesList().size()];
                                    for (int i = 0; i < data.length; i++) {
                                        data[i] = response.body().getModelCitiesList().get(i).getName();
                                        cityID[i]=response.body().getModelCitiesList().get(i).getId()+"";

                                    }
                                    ArrayAdapter<String> arrayAdapterCity = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, data);

                                    fnotificationBinding.selectCity.setAdapter(arrayAdapterCity);
                                    fnotificationBinding.selectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            sharedPreferences.edit().putString("city_id_notificatin",cityID[i]).apply();

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }



                            }

                            @Override
                            public void onFailure(Call<ModelCitiesManage> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ModelOstan> call, Throwable t) {

            }
        });



    }
}
