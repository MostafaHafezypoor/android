package mostafa.hafezypoor.nazri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.FrulesBinding;
import mostafa.hafezypoor.nazri.model.ModelRules;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FRules extends Fragment {
    private FrulesBinding frulesBinding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         frulesBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.frules,container,false);
        return frulesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RetrofitInit.getInstance().rules().enqueue(new Callback<ModelRules>() {
            @Override
            public void onResponse(Call<ModelRules> call, Response<ModelRules> response) {
                if (response.isSuccessful()){
                    frulesBinding.webView.loadDataWithBaseURL("",response.body().getRule(),"text/html","UTF-8","");
                }


            }

            @Override
            public void onFailure(Call<ModelRules> call, Throwable t) {

            }
        });
        frulesBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMain").commit();
            }
        });
    }
}
