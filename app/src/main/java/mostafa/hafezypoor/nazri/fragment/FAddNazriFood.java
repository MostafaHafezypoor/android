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

import com.google.gson.Gson;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.shawnlin.numberpicker.NumberPicker;

import java.io.IOException;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.FaddNazriFoodBinding;
import mostafa.hafezypoor.nazri.model.ModelCitiesManage;
import mostafa.hafezypoor.nazri.model.ModelOstan;
import mostafa.hafezypoor.nazri.model.ModelStatus;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAddNazriFood extends Fragment {
    private FaddNazriFoodBinding faddNazriFoodBinding;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        faddNazriFoodBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fadd_nazri_food, container, false);
        return faddNazriFoodBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] time = new String[24];
        for (int i = 0; i < time.length; i++) {
            time[i] = String.valueOf((i + 1));
        }
        faddNazriFoodBinding.clockEnd.setMinValue(1);
        faddNazriFoodBinding.clockEnd.setMaxValue(time.length);
        faddNazriFoodBinding.clockEnd.setDisplayedValues(time);
        faddNazriFoodBinding.clockEnd.setValue(7);
        faddNazriFoodBinding.clockStart.setMinValue(1);
        faddNazriFoodBinding.clockStart.setMaxValue(time.length);
        faddNazriFoodBinding.clockStart.setDisplayedValues(time);
        faddNazriFoodBinding.clockStart.setValue(7);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, new String[]{"شام", "ناهار", "صبحانه"});
        faddNazriFoodBinding.vadeFood.setAdapter(arrayAdapter);
        faddNazriFoodBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMain").commit();
            }
        });
        faddNazriFoodBinding.textRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FRules(),"FRules").commit();
            }
        });
        setOstan();

    }
 private boolean checkInput(){
        if (faddNazriFoodBinding.foodName.getText().toString().isEmpty()){
            faddNazriFoodBinding.foodName.setError("مقدار نام نمیتواند خالی باشد");
            return false;
        } else if (faddNazriFoodBinding.locaion.getText().toString().isEmpty()) {
            faddNazriFoodBinding.locaion.setError("مقدار نام نمیتواند خالی باشد");
            return false;
        }
        return true;
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
                faddNazriFoodBinding.spinnerOstan.setAdapter(arrayAdapterOstan);
                faddNazriFoodBinding.spinnerOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sharedPreferences.edit().putString("state_id",(i+1)+"").apply();
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

                                    faddNazriFoodBinding.selectCity.setAdapter(arrayAdapterCity);
                                    faddNazriFoodBinding.selectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                              sharedPreferences.edit().putString("city_id",cityID[i]).apply();

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
        faddNazriFoodBinding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.00,52.00),5));
                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        addMarker(point,null,null,null,mapboxMap,faddNazriFoodBinding.mapView,R.drawable.other_icon);
                        sharedPreferences.edit().putLong("lat", (long) point.getLatitude()).apply();
                        sharedPreferences.edit().putLong("long", (long) point.getLongitude()).apply();
                        return true;
                    }
                });
            }
        });
        faddNazriFoodBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    addFood();

                }

            }
        });

    }
    private void addFood(){
        String token=sharedPreferences.getString("token",null);
        String type;
        if (faddNazriFoodBinding.vadeFood.getSelectedItemPosition()==0){
            type ="dinner";
        } else if (faddNazriFoodBinding.vadeFood.getSelectedItemPosition()==1) {
            type="lunch";
        }else {
            type="breakfast";
        }

        String state_id=sharedPreferences.getString("state_id",null);
           String city_id=sharedPreferences.getString("city_id",null);
        if (sharedPreferences.getLong("lat",0)!=0&&sharedPreferences.getLong("long",0)!=0){
            double lat=sharedPreferences.getLong("lat",0);
            double longe=sharedPreferences.getLong("long",0);
             String dateStart=faddNazriFoodBinding.dateStart.getSelectedYear()+"-"+faddNazriFoodBinding.dateStart.getSelectedMonth()+"-"+faddNazriFoodBinding.dateStart.getSelectedDay()+" "+faddNazriFoodBinding.clockStart.getValue()+":00:00";
             String dateEnd=faddNazriFoodBinding.dateStart.getSelectedYear()+"-"+faddNazriFoodBinding.dateStart.getSelectedMonth()+"-"+faddNazriFoodBinding.dateStart.getSelectedDay()+" "+faddNazriFoodBinding.clockEnd.getValue()+":00:00";
           Log.i("TAG14001", "food_name "+faddNazriFoodBinding.foodName.getText().toString().trim()+" type "+type+" date strtt "+dateStart+" date end "+dateEnd+" state id "+state_id+" city id "+city_id+" location "+faddNazriFoodBinding.locaion.getText().toString()+" lat  "+lat+" longe "+longe);
            Log.i("TAG0095", "Bearer "+token);
             RetrofitInit.getInstance().add_food("Bearer "+token,faddNazriFoodBinding.foodName.getText().toString().trim(),type,dateStart,dateEnd,state_id,city_id,faddNazriFoodBinding.locaion.getText().toString(),lat,longe).enqueue(new Callback<ModelStatus>() {
                @Override
                public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "نذر شما ثبت شد", Toast.LENGTH_SHORT).show();
                       getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FRules").commit();
                        sharedPreferences.edit().putLong("lat",0).apply();
                        sharedPreferences.edit().putLong("longe",0).apply();

                    } else if (response.code()==400) {
                        try {
                            ModelStatus modelStatus=new Gson().fromJson(response.errorBody().string(), ModelStatus.class);
                            if (modelStatus.getMessage().equals("You cannot create a votive for the past tense")){
                                Toast.makeText(getContext(), "تاریخ وارد شده نمیتواند تاریخ گذشته باشد", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), modelStatus.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }

                @Override
                public void onFailure(Call<ModelStatus> call, Throwable t) {

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
