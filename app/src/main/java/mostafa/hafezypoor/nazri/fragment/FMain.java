package mostafa.hafezypoor.nazri.fragment;

import android.Manifest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import ir.map.sdk_map.MapirStyle;
import mostafa.hafezypoor.nazri.adapter.AdapterListCity;
import mostafa.hafezypoor.nazri.adapter.AdapterListOstan;
import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.adapter.AdapterNazrOnline;
import mostafa.hafezypoor.nazri.databinding.FmainBinding;
import mostafa.hafezypoor.nazri.dialog.DExit;
import mostafa.hafezypoor.nazri.dialog.DLogin;
import mostafa.hafezypoor.nazri.dialog.DShowTypeAddNazri;
import mostafa.hafezypoor.nazri.model.ModelCities;
import mostafa.hafezypoor.nazri.model.ModelCitiesManage;
import mostafa.hafezypoor.nazri.model.ModelOstan;
import mostafa.hafezypoor.nazri.model.ModelOstanState;
import mostafa.hafezypoor.nazri.model.ModelVotive;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FMain extends Fragment {
    private FmainBinding fmainBinding;
    private SharedPreferences sharedPreferences;
    private MapboxMap mapboxMap;
    private LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    int REQUESTPER = 1000065555;

    public MapboxMap getMapboxMap() {
        return mapboxMap;
    }

    public void setMapboxMap(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContext().getTheme().applyStyle(com.google.android.material.R.style.Theme_MaterialComponents_Light, true);



        sharedPreferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("type", null) == null) {
            sharedPreferences.edit().putString("type", "0").apply();
        }
        if (sharedPreferences.getInt("state", 0) == 0) {
            sharedPreferences.edit().putInt("state", 0).apply();
        }
        if (sharedPreferences.getInt("city", 0) == 0) {
            sharedPreferences.edit().putInt("city", 0).apply();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            fmainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fmain, container, false);

        return fmainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        if (sharedPreferences.getBoolean("on_notificatin",false)==true){
//            Intent intent=new Intent(getActivity(), ServiceNotification.class);
//            getActivity().startService(intent);
//        }

        fmainBinding.fillterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BottomSheetBehavior.from(fmainBinding.bottomSheet).getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    BottomSheetBehavior.from(fmainBinding.bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    BottomSheetBehavior.from(fmainBinding.bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);

                }
            }
        });


       fmainBinding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(new Style.Builder().fromUri(MapirStyle.MAIN_MOBILE_VECTOR_STYLE), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        setMapboxMap(mapboxMap);
                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.00, 53.00), 4));
                        addMarks(mapboxMap);
                        fmainBinding.myLocation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUESTPER);
                                } else {
                                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, new LocationListener() {
                                        @Override
                                        public void onLocationChanged(@NonNull Location location) {
                                            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));


                                        }

                                        @Override
                                        public void onProviderDisabled(@NonNull String provider) {
                                            LocationListener.super.onProviderDisabled(provider);
                                            Toast.makeText(getContext(), "location خود را فعال کنید", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onProviderEnabled(@NonNull String provider) {
                                            LocationListener.super.onProviderEnabled(provider);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });

            }
        });


        setOnlineNazri();
        manageBottomNav();
        manageFabAdd();
        //show map for user below code

        manageFilter();//filter manager
        //corner bottomApp below code
        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable) fmainBinding.bottomAppBar.getBackground();
        materialShapeDrawable.setShapeAppearanceModel(materialShapeDrawable.getShapeAppearanceModel().toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, 50)
                .setTopLeftCorner(CornerFamily.ROUNDED, 50)
                .build());

    }


    private void manageFilter() {
        // for show result filter user
        fmainBinding.viewFlpperFMain.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        fmainBinding.viewFlpperFMain.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        fmainBinding.allNazri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmainBinding.viewFlpperFMain.showNext();

                sharedPreferences.edit().putString("type", "0").apply();
            }
        });
        fmainBinding.foodNazri.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fmainBinding.viewFlpperFMain.showNext();

                sharedPreferences.edit().putString("type", "1").apply();
            }
        });
        fmainBinding.bookNazri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmainBinding.viewFlpperFMain.showNext();

                sharedPreferences.edit().putString("type", "2").apply();
            }
        });
        fmainBinding.otherNazri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmainBinding.viewFlpperFMain.showNext();

                sharedPreferences.edit().putString("type", "4").apply();
            }
        });
        fmainBinding.listOstan.setLayoutManager(new LinearLayoutManager(getContext()));

        RetrofitInit.getInstance().getOstanState().enqueue(new Callback<ModelOstan>() {
            @Override
            public void onResponse(Call<ModelOstan> call, Response<ModelOstan> response) {
                if (response.isSuccessful()) {
                    AdapterListOstan adapterListOstan = new AdapterListOstan(getContext(), response.body());
                    fmainBinding.listOstan.setAdapter(adapterListOstan);
                    adapterListOstan.setOnstanClickManager(new AdapterListOstan.OnstanClickManager() {
                        @Override
                        public void onSelectOstan(ModelOstanState modelOstanState) {
                            fmainBinding.viewFlpperFMain.showNext();
                            sharedPreferences.edit().putInt("state", modelOstanState.getId()).apply();
                            RetrofitInit.getInstance().getCities(modelOstanState.getId()).enqueue(new Callback<ModelCitiesManage>() {
                                @Override
                                public void onResponse(Call<ModelCitiesManage> call, Response<ModelCitiesManage> response) {
                                    if (response.isSuccessful()) {
                                        AdapterListCity adapterListCity = new AdapterListCity(getContext(), response.body().getModelCitiesList());
                                        fmainBinding.listCity.setLayoutManager(new LinearLayoutManager(getContext()));
                                        fmainBinding.listCity.setAdapter(adapterListCity);
                                        adapterListCity.setOnstanClickManager(new AdapterListCity.OnCityClickManager() {
                                            @Override
                                            public void onSelectCity(ModelCities modelCities) {
                                                BottomSheetBehavior.from(fmainBinding.bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
                                                sharedPreferences.edit().putInt("city", modelCities.getId()).apply();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame_layout, new FMain(), "ddfdffaafa").commit();

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<ModelCitiesManage> call, Throwable t) {
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelOstan> call, Throwable t) {

            }
        });

    }


    private void manageBottomNav() {
        fmainBinding.botomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nazriha) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame_layout, new FShowAllNari(), "fshoMap").commit();
                } else if (item.getItemId() == R.id.exit) {
                    if (sharedPreferences.getString("token", null) != null) {
                        DExit dExit = new DExit(getContext());
                        dExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dExit.show();
                    } else {
                        Toast.makeText(getContext(), "شما هنوز وارد داشبورد نشده اید!", Toast.LENGTH_SHORT).show();
                    }

                } else if (item.getItemId() == R.id.hamyari) {
                    if (sharedPreferences.getString("token", null) == null) {
                        DLogin dLogin = new DLogin(getContext());
                        dLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dLogin.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        dLogin.show();
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame_layout, new FDonates(), "fdonate").commit();
                    }
                } else if (item.getItemId() == R.id.dashboard) {
                    if (sharedPreferences.getString("token", null) == null) {
                        DLogin dLogin = new DLogin(getContext());
                        dLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dLogin.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        dLogin.show();
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame_layout, new FDashboard(), "ddfaasdfffaad").commit();
                    }
                }
                return false;
            }
        });
    }

    private void manageFabAdd() {
        fmainBinding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("token", null) == null) {
                    DLogin dLogin = new DLogin(getContext());
                    dLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dLogin.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dLogin.show();
                } else {
                    DShowTypeAddNazri dShowTypeAddNazri = new DShowTypeAddNazri(getContext());
                    dShowTypeAddNazri.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dShowTypeAddNazri.show();
                }
            }
        });
    }

    private void addMarks(MapboxMap mapboxMap) {

        String type = sharedPreferences.getString("type", null);
        int state = sharedPreferences.getInt("state", 0);
        String city = sharedPreferences.getInt("city", 0) + "";
        RetrofitInit.getInstance().getVotive(type, state, city).enqueue(new Callback<ModelVotive>() {
            @Override
            public void onResponse(Call<ModelVotive> call, Response<ModelVotive> response) {
                if (response.isSuccessful()) {
                    if (type.equals("1")) {
                        addMarksFood(mapboxMap);
                    } else if (type.equals("4")) {
                        addOthers(mapboxMap);
                    } else if (type.equals("2")) {
                        addBoooks(mapboxMap);
                    } else if (type.equals("0")) {
                        addMarksFood(mapboxMap);
                        addOthers(mapboxMap);
                        addBoooks(mapboxMap);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVotive> call, Throwable t) {

            }
        });
    }

    private void addMarksFood(MapboxMap mapboxMap) {

        RetrofitInit.getInstance().getVotive(sharedPreferences.getString("type", null), sharedPreferences.getInt("state", 0), sharedPreferences.getInt("city", 0) + "").enqueue(new Callback<ModelVotive>() {
            @Override
            public void onResponse(Call<ModelVotive> call, Response<ModelVotive> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelFoods().size(); i++) {
                        double lat = Double.parseDouble(response.body().getModelVoiveVotives().getModelFoods().get(i).getLatitude());
                        double lng = Double.parseDouble(response.body().getModelVoiveVotives().getModelFoods().get(i).getLongitude());
                        String title = response.body().getModelVoiveVotives().getModelFoods().get(i).getName();
                        String sniipe = response.body().getModelVoiveVotives().getModelFoods().get(i).getName();
                        addMarker(new LatLng(lat, lng), title, sniipe, null, mapboxMap, fmainBinding.mapView, R.drawable.food_icon);

                    }
                }


            }

            @Override
            public void onFailure(Call<ModelVotive> call, Throwable t) {

            }
        });
    }

    private void addBoooks(MapboxMap mapboxMap) {
        RetrofitInit.getInstance().getVotive(sharedPreferences.getString("type", null), sharedPreferences.getInt("state", 0), sharedPreferences.getInt("city", 0) + "").enqueue(
                new Callback<ModelVotive>() {
                    @Override
                    public void onResponse(Call<ModelVotive> call, Response<ModelVotive> response) {
                        for (int i = 0; i < response.body().getModelVoiveVotives().getModelBooks().size(); i++) {
                            double lat = Double.parseDouble(response.body().getModelVoiveVotives().getModelBooks().get(i).getLatitude());
                            double lng = Double.parseDouble(response.body().getModelVoiveVotives().getModelBooks().get(i).getLongitude());
                            String title = response.body().getModelVoiveVotives().getModelBooks().get(i).getName();
                            String sniipe = response.body().getModelVoiveVotives().getModelBooks().get(i).getDescription();
                            addMarker(new LatLng(lat, lng), title, sniipe, null, mapboxMap, fmainBinding.mapView, R.drawable.book_icon);

                        }
                    }

                    @Override
                    public void onFailure(Call<ModelVotive> call, Throwable t) {

                    }
                }
        );

    }

    private void addOthers(MapboxMap mapboxMap) {
        mapboxMap.addOnMoveListener(new MapboxMap.OnMoveListener() {
            @Override
            public void onMoveBegin(@NonNull MoveGestureDetector detector) {
                if (manageBottomSheete != null) {
                    manageBottomSheete.onClick(getView());
                }

            }

            @Override
            public void onMove(@NonNull MoveGestureDetector detector) {
            }

            @Override
            public void onMoveEnd(@NonNull MoveGestureDetector detector) {

            }
        });
        RetrofitInit.getInstance().getVotive(sharedPreferences.getString("type", null), sharedPreferences.getInt("state", 0), sharedPreferences.getInt("city", 0) + "").enqueue(
                new Callback<ModelVotive>() {
                    @Override
                    public void onResponse(Call<ModelVotive> call, Response<ModelVotive> response) {
                        for (int i = 0; i < response.body().getModelVoiveVotives().getModelOthers().size(); i++) {
                            double lat = Double.parseDouble(response.body().getModelVoiveVotives().getModelOthers().get(i).getLatitude());
                            double lng = Double.parseDouble(response.body().getModelVoiveVotives().getModelOthers().get(i).getLongitude());
                            String title = response.body().getModelVoiveVotives().getModelOthers().get(i).getTitle();
                            String sniipe = response.body().getModelVoiveVotives().getModelOthers().get(i).getDescription();
                            addMarker(new LatLng(lat, lng), title, sniipe, null, mapboxMap, fmainBinding.mapView, R.drawable.other_icon);

                        }
                    }

                    @Override
                    public void onFailure(Call<ModelVotive> call, Throwable t) {

                    }
                }
        );

    }

    private void addMarker(LatLng position, String title, String snippet, Marker myMarker, MapboxMap map, MapView mapView, int icon_res) {
        IconFactory iconFactory = IconFactory.getInstance(getContext());

        Icon icon = iconFactory.fromResource(icon_res);

        myMarker = map.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet)
                .icon(icon)

        );

        myMarker.showInfoWindow(map, mapView);
    }

    private View.OnClickListener manageBottomSheete;


    private void setOnlineNazri() {
        RetrofitInit.getInstance().getVotive("0", 0, "0").enqueue(new Callback<ModelVotive>() {
            @Override
            public void onResponse(Call<ModelVotive> call, Response<ModelVotive> response) {
                if (response.isSuccessful()) {
                    fmainBinding.listNazrOnline.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    fmainBinding.listNazrOnline.setAdapter(new AdapterNazrOnline(getContext(), response.body().getModelVoiveVotives().getModelOnlines()));
                }
            }

            @Override
            public void onFailure(Call<ModelVotive> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTPER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "run", Toast.LENGTH_SHORT).show();
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10000, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                      if (getMapboxMap()==null){
                          Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                      }else {
                          getMapboxMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));

                      }

                    }
                    @Override
                    public void onProviderDisabled(@NonNull String provider) {
                        LocationListener.super.onProviderDisabled(provider);
                        Toast.makeText(getContext(), "location خود را فعال کنید", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {
                        LocationListener.super.onProviderEnabled(provider);
                    }
                });
            }
        }
    }
}
