package mostafa.hafezypoor.nazri.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.FaddNazriOrtherBinding;
import mostafa.hafezypoor.nazri.model.ModelCitiesManage;
import mostafa.hafezypoor.nazri.model.ModelOstan;
import mostafa.hafezypoor.nazri.model.ModelStatus;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAddNazriOther extends Fragment {
    private SharedPreferences sharedPreferences;
    private FaddNazriOrtherBinding faddNazriOrtherBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        faddNazriOrtherBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fadd_nazri_orther, container, false);
        return faddNazriOrtherBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        faddNazriOrtherBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    addOther();
                }

            }
        });

        faddNazriOrtherBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMain").commit();
            }
        });
        faddNazriOrtherBinding.textRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FRules(),"FRules").commit();
            }
        });
        faddNazriOrtherBinding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.00,52.00),5));
                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        addMarker(point,null,null,null,mapboxMap,faddNazriOrtherBinding.mapView,R.drawable.other_icon);
                        sharedPreferences.edit().putLong("lat", (long) point.getLatitude()).apply();
                        sharedPreferences.edit().putLong("long", (long) point.getLongitude()).apply();
                        return true;
                    }
                });
            }
        });
        setOstan();

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
                faddNazriOrtherBinding.spinnerOstan.setAdapter(arrayAdapterOstan);
                faddNazriOrtherBinding.spinnerOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sharedPreferences.edit().putString("state_id", (i + 1) + "").apply();
                        RetrofitInit.getInstance().getCities(response.body().getModelOstanStates().get(i).getId()).enqueue(new Callback<ModelCitiesManage>() {
                            @Override
                            public void onResponse(Call<ModelCitiesManage> call, Response<ModelCitiesManage> response) {
                                if (response.isSuccessful()) {
                                    String[] data = new String[response.body().getModelCitiesList().size()];
                                    String[] cityID = new String[response.body().getModelCitiesList().size()];
                                    for (int i = 0; i < data.length; i++) {
                                        data[i] = response.body().getModelCitiesList().get(i).getName();
                                        cityID[i] = response.body().getModelCitiesList().get(i).getId() + "";

                                    }
                                    ArrayAdapter<String> arrayAdapterCity = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, data);

                                    faddNazriOrtherBinding.selectCity.setAdapter(arrayAdapterCity);
                                    faddNazriOrtherBinding.selectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            sharedPreferences.edit().putString("city_id", cityID[i]).apply();

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
    private boolean checkInput(){
        if (faddNazriOrtherBinding.description.getText().toString().isEmpty()){
            faddNazriOrtherBinding.description.setError("توضیحات نباید خالی باشد");
            return false;
        } else if (faddNazriOrtherBinding.titleName.getText().toString().isEmpty()) {
            faddNazriOrtherBinding.titleName.setError("نام نباید خالی باشد");
            return false;
        }else if (faddNazriOrtherBinding.locaion.getText().toString().isEmpty()) {
            faddNazriOrtherBinding.locaion.setError("آدرس نباید خالی باشد");
            return false;
        }
        return true;
    }
    private void addOther(){
        String token=sharedPreferences.getString("token",null);
        String state_id=sharedPreferences.getString("state_id",null);
        String city_id=sharedPreferences.getString("city_id",null);
        if (sharedPreferences.getLong("lat",0)!=0&&sharedPreferences.getLong("long",0)!=0) {
            double lat = sharedPreferences.getLong("lat", 0);
            double longe = sharedPreferences.getLong("long", 0);
            RetrofitInit.getInstance().add_other("Bearer "+token,faddNazriOrtherBinding.titleName.getText().toString(),faddNazriOrtherBinding.description.getText().toString(),state_id,city_id,faddNazriOrtherBinding.locaion.getText().toString(),lat,longe).enqueue(new Callback<ModelStatus>() {
                @Override
                public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                    if (response.isSuccessful())
                    {
                        Toast.makeText(getContext(), "نذر شما ثبت شد", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FRules").commit();
                        sharedPreferences.edit().putLong("lat",0).apply();
                        sharedPreferences.edit().putLong("longe",0).apply();
                    }
                    Toast.makeText(getContext(), response.code()+"", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ModelStatus> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getContext(), "ابتدا موقعیت را از روی نقشه انتخاب کنید", Toast.LENGTH_SHORT).show();

        }

    }
    private void addMarker(LatLng position, String title, String snippet, Marker myMarker, MapboxMap map, MapView mapView, int icon_res) {
        IconFactory iconFactory = IconFactory.getInstance(getContext());

        Icon icon =iconFactory.fromResource(icon_res);

        myMarker = map.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet)
                .icon(icon)

        );

        myMarker.showInfoWindow(map, mapView);
    }
}
