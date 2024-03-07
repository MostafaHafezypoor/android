package mostafa.hafezypoor.nazri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.FshowDetailNazriBinding;
import mostafa.hafezypoor.nazri.model.ModelAdapterShowAllNazri;

public class FShowDetailNazri extends Fragment {
    private FshowDetailNazriBinding fshowDetailNazriBinding;
    private ModelAdapterShowAllNazri modelAdapterShowAllNazri;

    public FShowDetailNazri(ModelAdapterShowAllNazri modelAdapterShowAllNazri) {
        this.modelAdapterShowAllNazri = modelAdapterShowAllNazri;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       fshowDetailNazriBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.fshow_detail_nazri,container,false);
        return fshowDetailNazriBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fshowDetailNazriBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMaine").commit();
            }
        });

        if (modelAdapterShowAllNazri.getLatitude()!=null&modelAdapterShowAllNazri.getLongitude()!=null&modelAdapterShowAllNazri.getTypeVotive().equals("online")==false){
            showMap(modelAdapterShowAllNazri.getLatitude(),modelAdapterShowAllNazri.getLongitude(), modelAdapterShowAllNazri.getDescription(), modelAdapterShowAllNazri.getDescription());
        }
        if (modelAdapterShowAllNazri.getLocation()!=null)
            fshowDetailNazriBinding.location.setText(" آدرس : "+modelAdapterShowAllNazri.getLocation());
        if (modelAdapterShowAllNazri.getDate()!=null)
            fshowDetailNazriBinding.datePublish.setText(modelAdapterShowAllNazri.getDate());
        if (modelAdapterShowAllNazri.getName()!=null)
            fshowDetailNazriBinding.nazriTopName.setText(modelAdapterShowAllNazri.getName());
        if (modelAdapterShowAllNazri.getTypeVotive().equals("food")){
            if (modelAdapterShowAllNazri.getType().equals("dinner")){
                fshowDetailNazriBinding.nazrType.setText("نذر غذا برای شام");
            } else if (modelAdapterShowAllNazri.getType().equals("breakfast")) {
                fshowDetailNazriBinding.nazrType.setText("نذر غذا برای صبحانه");
            } else if (modelAdapterShowAllNazri.getType().equals("lunch")) {
                fshowDetailNazriBinding.nazrType.setText("نذر غذا برای ناهار");
            }
        }else if (modelAdapterShowAllNazri.getTypeVotive().equals("book")) {
            fshowDetailNazriBinding.nazrType.setText("نذر کتاب عمومی");
        }else if (modelAdapterShowAllNazri.getTypeVotive().equals("other")) {
            fshowDetailNazriBinding.nazrType.setText("نذر سایر");
        }else if (modelAdapterShowAllNazri.getTypeVotive().equals("online")){
            fshowDetailNazriBinding.nazrType.setText("نذر آنلاین");
        }
    }
    private void showMap(String lat,String log,String title,String sniiped){
        double latude=Double.parseDouble(lat);
        double longe=Double.parseDouble(log);
        fshowDetailNazriBinding.mapView.setVisibility(View.VISIBLE);
        fshowDetailNazriBinding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
             mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latude,longe),10f));
             addMarker(new LatLng(latude,longe),title,sniiped,null,mapboxMap,fshowDetailNazriBinding.mapView,R.drawable.other_icon);
            }
        });
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
