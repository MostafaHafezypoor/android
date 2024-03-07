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
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.adapter.AdapterShowAllNazri;
import mostafa.hafezypoor.nazri.databinding.FshowAllNaziBinding;
import mostafa.hafezypoor.nazri.model.ModelAdapterShowAllNazri;
import mostafa.hafezypoor.nazri.model.ModelBooks;
import mostafa.hafezypoor.nazri.model.ModelFoods;
import mostafa.hafezypoor.nazri.model.ModelOnlines;
import mostafa.hafezypoor.nazri.model.ModelOthers;
import mostafa.hafezypoor.nazri.model.ModelVotive;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FShowAllNari extends Fragment implements View.OnClickListener {
    private FshowAllNaziBinding fshowAllNaziBinding;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fshowAllNaziBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.fshow_all_nazi,container,false);
       btnAllNazr();
        fshowAllNaziBinding.btnAllNazri.setBackgroundColor(getActivity().getColor(R.color.green));
        showNazri("0");

        return fshowAllNaziBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fshowAllNaziBinding.btnAllNazri.setOnClickListener(this);
        fshowAllNaziBinding.btnNazzrOnline.setOnClickListener(this);
        fshowAllNaziBinding.btnNazrFood.setOnClickListener(this);
        fshowAllNaziBinding.btnOtherNazri.setOnClickListener(this);
        fshowAllNaziBinding.btnNazrBook.setOnClickListener(this);
        fshowAllNaziBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMainBack").commit();
            }
        });

    }
    private void showNazri(String type){
        fshowAllNaziBinding.listNazri.setLayoutManager(new LinearLayoutManager(getContext()));

        RetrofitInit.getInstance().getVotive(type,0,"0").enqueue(new Callback<ModelVotive>() {
            @Override
            public void onResponse(Call<ModelVotive> call, Response<ModelVotive> response) {
            if (response.isSuccessful()){
                List<ModelAdapterShowAllNazri>modelAdapterShowAllNazris=new ArrayList<>();
                if (type.equals("0")){
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelFoods().size(); i++) {
                        ModelFoods modelFoods=response.body().getModelVoiveVotives().getModelFoods().get(i);
                      modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("food", modelFoods.getType(), modelFoods.getName(), null, modelFoods.getLocation(), modelFoods.getLatitude(), modelFoods.getLongitude(), modelFoods.getDate(),null,null, modelFoods.getStart_at()+" الی "+modelFoods.getEnd_at()));
                    }
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelBooks().size(); i++) {
                        ModelBooks modelBooks=response.body().getModelVoiveVotives().getModelBooks().get(i);
                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("book",modelBooks.getType(),modelBooks.getName(),modelBooks.getDescription(),modelBooks.getLocation(), modelBooks.getLatitude(), modelBooks.getLongitude(), modelBooks.getDate(), modelBooks.getWriter(), null, modelBooks.getActive_time()));

                    }
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelOthers().size(); i++) {
                        ModelOthers modelOthers=response.body().getModelVoiveVotives().getModelOthers().get(i);
                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("other",null,modelOthers.getTitle(), modelOthers.getDescription(), modelOthers.getLocation(), modelOthers.getLatitude(), modelOthers.getLongitude(), modelOthers.getDate(), null,null,null));
                    }
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelOnlines().size(); i++) {
                        ModelOnlines modelOnlines=response.body().getModelVoiveVotives().getModelOnlines().get(i);
                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("online",null,modelOnlines.getTitle(), modelOnlines.getDescription(), modelOnlines.getSite_name(),null, null, modelOnlines.getDate(), null, modelOnlines.getSite_url(),null));
                    }
                } else if (type.equals("1")) {
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelFoods().size(); i++) {

                        ModelFoods modelFoods=response.body().getModelVoiveVotives().getModelFoods().get(i);
                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("food", modelFoods.getType(), modelFoods.getName(), null, modelFoods.getLocation(), modelFoods.getLatitude(), modelFoods.getLongitude(), modelFoods.getDate(),null,null,null));
                    }
                } else if (type.equals("2")) {
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelBooks().size(); i++) {
                        ModelBooks modelBooks=response.body().getModelVoiveVotives().getModelBooks().get(i);
                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("book",modelBooks.getType(),modelBooks.getName(),modelBooks.getDescription(),modelBooks.getLocation(), modelBooks.getLatitude(), modelBooks.getLongitude(), modelBooks.getDate(), modelBooks.getWriter(), null, modelBooks.getActive_time()));

                    }
                } else if (type.equals("3")) {
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelOnlines().size(); i++) {
                        ModelOnlines modelOnlines=response.body().getModelVoiveVotives().getModelOnlines().get(i);
                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("online",null,modelOnlines.getTitle(), modelOnlines.getDescription(), modelOnlines.getSite_name(),null, null, modelOnlines.getDate(), null, modelOnlines.getSite_url(),null));
                    }
                } else if (type.equals("4")) {
                    for (int i = 0; i < response.body().getModelVoiveVotives().getModelOthers().size(); i++) {
                        ModelOthers modelOthers=response.body().getModelVoiveVotives().getModelOthers().get(i);
                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri("other",null,modelOthers.getTitle(), modelOthers.getDescription(), modelOthers.getLocation(), modelOthers.getLatitude(), modelOthers.getLongitude(), modelOthers.getDate(), null,null,null));
                    }
                }
                fshowAllNaziBinding.listNazri.setAdapter(new AdapterShowAllNazri(getContext(),modelAdapterShowAllNazris));
            }
            }

            @Override
            public void onFailure(Call<ModelVotive> call, Throwable t) {

            }
        });
    }
    private void btnAllNazr(){
        if (sharedPreferences.getBoolean("btnAllNazri",true)){
            fshowAllNaziBinding.btnAllNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            sharedPreferences.edit().putBoolean("btnAllNazri",false).apply();
        }else {
            sharedPreferences.edit().putBoolean("btnAllNazri",true).apply();
            sharedPreferences.edit().putBoolean("btnNazrBook", false).apply();
            sharedPreferences.edit().putBoolean("btnNazrFood", false).apply();
            sharedPreferences.edit().putBoolean("btnNazrOnline", false).apply();
            sharedPreferences.edit().putBoolean("btnOtherNazri", false).apply();
            fshowAllNaziBinding.btnAllNazri.setBackgroundColor(getActivity().getColor(R.color.green));
            fshowAllNaziBinding.btnNazrBook.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrFood.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnOtherNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            showNazri("0");
        }

    }
    private void btnNazrFood() {
        if (sharedPreferences.getBoolean("btnNazrFood", true)) {
            fshowAllNaziBinding.btnNazrFood.setBackgroundColor(getActivity().getColor(R.color.gray));
            sharedPreferences.edit().putBoolean("btnNazrFood", false).apply();
        } else {
          sharedPreferences.edit().putBoolean("btnNazrFood", true).apply();
            sharedPreferences.edit().putBoolean("btnNazrBook", false).apply();
            sharedPreferences.edit().putBoolean("btnAllNazri",false).apply();
            sharedPreferences.edit().putBoolean("btnNazrOnline", false).apply();
            sharedPreferences.edit().putBoolean("btnOtherNazri", false).apply();
            fshowAllNaziBinding.btnAllNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrBook.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrFood.setBackgroundColor(getActivity().getColor(R.color.green));
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnOtherNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            showNazri("1");
        }

    }
    private void btnNazrOnline() {
        if (sharedPreferences.getBoolean("btnNazrOnline", true)) {
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.gray));
            sharedPreferences.edit().putBoolean("btnNazrOnline", false).apply();
        } else {
            sharedPreferences.edit().putBoolean("btnNazrOnline", true).apply();
            sharedPreferences.edit().putBoolean("btnNazrBook", false).apply();
            sharedPreferences.edit().putBoolean("btnAllNazri", false).apply();
            sharedPreferences.edit().putBoolean("btnNazrFood", false).apply();
            sharedPreferences.edit().putBoolean("btnOtherNazri", false).apply();
            fshowAllNaziBinding.btnAllNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrBook.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrFood.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.green));
            fshowAllNaziBinding.btnOtherNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            showNazri("3");
        }
    }
    private void btnNazrBook() {
        if (sharedPreferences.getBoolean("btnNazrBook", true)) {
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.gray));
            sharedPreferences.edit().putBoolean("btnNazrBook", false).apply();
        } else {
            sharedPreferences.edit().putBoolean("btnNazrBook", true).apply();
            sharedPreferences.edit().putBoolean("btnNazrOnline", false).apply();
            sharedPreferences.edit().putBoolean("btnAllNazri", false).apply();
            sharedPreferences.edit().putBoolean("btnNazrFood", false).apply();
            sharedPreferences.edit().putBoolean("btnOtherNazri", false).apply();
            fshowAllNaziBinding.btnAllNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrBook.setBackgroundColor(getActivity().getColor(R.color.green));
            fshowAllNaziBinding.btnNazrFood.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnOtherNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            showNazri("2");
        }
    }
    private void btnOtherNazri() {
        if (sharedPreferences.getBoolean("btnOtherNazri", true)) {
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.gray));
            sharedPreferences.edit().putBoolean("btnOtherNazri", false).apply();
        } else {
            sharedPreferences.edit().putBoolean("btnOtherNazri", true).apply();
            sharedPreferences.edit().putBoolean("btnNazrBook", false).apply();
            sharedPreferences.edit().putBoolean("btnNazrOnline", false).apply();
            sharedPreferences.edit().putBoolean("btnAllNazri", false).apply();
            sharedPreferences.edit().putBoolean("btnNazrFood", false).apply();
            fshowAllNaziBinding.btnAllNazri.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrBook.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazrFood.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnNazzrOnline.setBackgroundColor(getActivity().getColor(R.color.gray));
            fshowAllNaziBinding.btnOtherNazri.setBackgroundColor(getActivity().getColor(R.color.green));
            showNazri("4");
        }
    }
        @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnAllNazri){
     btnAllNazr();
        }else if (view.getId()==R.id.btnNazrFood){
           btnNazrFood();
       } else if (view.getId()==R.id.btnNazzrOnline) {
            btnNazrOnline();
        } else if (view.getId()==R.id.btnNazrBook) {
btnNazrBook();
        } else if (view.getId()==R.id.btnOtherNazri) {
btnOtherNazri();
        }
        }
}
