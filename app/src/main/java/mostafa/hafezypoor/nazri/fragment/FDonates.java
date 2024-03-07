package mostafa.hafezypoor.nazri.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.adapter.AdapterListDonate;
import mostafa.hafezypoor.nazri.databinding.FdonateBinding;
import mostafa.hafezypoor.nazri.model.ModelDonate;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FDonates extends Fragment {
    private FdonateBinding fdonateBinding;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fdonateBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.fdonate,container,false);
        return fdonateBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fdonateBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMaine").commit();
            }
        });
        RetrofitInit.getInstance().donates("Bearer "+sharedPreferences.getString("token",null)).enqueue(new Callback<ModelDonate>() {
            @Override
            public void onResponse(Call<ModelDonate> call, Response<ModelDonate> response) {
                if (response.isSuccessful()){
                    fdonateBinding.listDonate.setLayoutManager(new LinearLayoutManager(getContext()));
                    fdonateBinding.listDonate.setAdapter(new AdapterListDonate(getContext(), response.body()));

                }
            }

            @Override
            public void onFailure(Call<ModelDonate> call, Throwable t) {


            }
        });

    }
}
