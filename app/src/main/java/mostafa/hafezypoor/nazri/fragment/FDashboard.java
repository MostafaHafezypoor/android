package mostafa.hafezypoor.nazri.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.adapter.AdapterMyVotive;
import mostafa.hafezypoor.nazri.databinding.FdoshboardBinding;
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

public class FDashboard extends Fragment {
private FdoshboardBinding fdoshboardBinding;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       fdoshboardBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.fdoshboard,container,false);
        return fdoshboardBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fdoshboardBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FMain(),"FMain").commit();
            }
        });
        handlerToolbarItemMenu();
        setShowAllNazri();
        fdoshboardBinding.radioGroupStatus.check(R.id.rall);
        fdoshboardBinding.radioGroupNazriType.check(R.id.nall);
        fdoshboardBinding.radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=getActivity().findViewById(i);
                if (radioButton.getId()==R.id.rall){
                       sharedPreferences.edit().putString("status","all").apply();
                } else if (radioButton.getId()==R.id.rpending) {
                    sharedPreferences.edit().putString("status","Pending").apply();

                }else if (radioButton.getId()==R.id.rexpire) {
                    sharedPreferences.edit().putString("status","expire").apply();

                }else if (radioButton.getId()==R.id.rrejected) {
                    sharedPreferences.edit().putString("status","rejected").apply();

                }else if (radioButton.getId()==R.id.rAcive) {
                    sharedPreferences.edit().putString("status","Active").apply();

                }
                setShowAllNazri();
            }
        });
        fdoshboardBinding.radioGroupNazriType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
          RadioButton radioButton=getActivity().findViewById(i);
          if (radioButton.getId()==R.id.nall){
              sharedPreferences.edit().putString("type","all").apply();
          } else if (radioButton.getId()==R.id.nfood) {
              sharedPreferences.edit().putString("type","food").apply();
          }else if (radioButton.getId()==R.id.nbook) {
              sharedPreferences.edit().putString("type","book").apply();
          }else if (radioButton.getId()==R.id.nonline) {
              sharedPreferences.edit().putString("type","online").apply();
          }else if (radioButton.getId()==R.id.nother) {
              sharedPreferences.edit().putString("type","other").apply();
          }
setShowAllNazri();
            }
        });

    }

    private void handlerToolbarItemMenu(){
        fdoshboardBinding.toolbar.inflateMenu(R.menu.menu_dashboard);

        fdoshboardBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              if (item.getItemId()==R.id.ruls) {
                  getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FRules(),"FddddaMain").commit();
              } else if (item.getItemId()==R.id.notification) {
                  getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FNotification(),"FddadsadfdfddaMain").commit();

              }
                return true;
            }
        });
    }
    private void setShowAllNazri(){
        String token=sharedPreferences.getString("token",null);
        String type=sharedPreferences.getString("type","all");
        String status=sharedPreferences.getString("status","all");
        RetrofitInit.getInstance().getMyVotive("Bearer "+token).enqueue(new Callback<ModelVotive>() {
            @Override
            public void onResponse(Call<ModelVotive> call, Response<ModelVotive> response) {

                List<ModelAdapterShowAllNazri> modelAdapterShowAllNazris = new ArrayList<>();
                if (response.isSuccessful()) {

                    if (!type.equals("all")) {
                        if (type.equals("food")) {
                            for (int i = 0; i < response.body().getModelVoiveVotives().getModelFoods().size(); i++) {
                                if (!status.equals("all")){
                                    if (response.body().getModelVoiveVotives().getModelFoods().get(i).getStatus().equals(status)) {
                                        ModelFoods modelFoods = response.body().getModelVoiveVotives().getModelFoods().get(i);
                                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelFoods.getId()+"","food", modelFoods.getFood_type(), modelFoods.getFood_name(), null, modelFoods.getLocation(), modelFoods.getLatitude(), modelFoods.getLongitude(), modelFoods.getDate(), null, null, modelFoods.getStart_at() + " الی " + modelFoods.getEnd_at(), modelFoods.getStatus()));
                                    }
                                }else {
                                    ModelFoods modelFoods = response.body().getModelVoiveVotives().getModelFoods().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelFoods.getId()+"","food", modelFoods.getFood_type(), modelFoods.getFood_name(), null, modelFoods.getLocation(), modelFoods.getLatitude(), modelFoods.getLongitude(), modelFoods.getDate(), null, null, modelFoods.getStart_at() + " الی " + modelFoods.getEnd_at(), modelFoods.getStatus()));
                                }

                            }
                        } else if (type.equals("book")) {
                            for (int i = 0; i < response.body().getModelVoiveVotives().getModelBooks().size(); i++) {
                                if (!status.equals("all")) {
                                    if (response.body().getModelVoiveVotives().getModelBooks().get(i).getStatus().equals(status)) {
                                        ModelBooks modelBooks = response.body().getModelVoiveVotives().getModelBooks().get(i);

                                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelBooks.getId()+"","book", modelBooks.getBook_type(), modelBooks.getBook_name(), modelBooks.getDescription(), modelBooks.getLocation(), modelBooks.getLatitude(), modelBooks.getLongitude(), modelBooks.getDate(), modelBooks.getWriter(), null, modelBooks.getActive_time(), modelBooks.getStatus()));
                                    }
                                } else {
                                    ModelBooks modelBooks = response.body().getModelVoiveVotives().getModelBooks().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelBooks.getId()
                                            +"","book", modelBooks.getBook_type(), modelBooks.getBook_name(), modelBooks.getDescription(), modelBooks.getLocation(), modelBooks.getLatitude(), modelBooks.getLongitude(), modelBooks.getDate(), modelBooks.getWriter(), null, modelBooks.getActive_time(), modelBooks.getStatus()));

                                }
                            }
                        } else if (type.equals("other")) {
                            for (int i = 0; i < response.body().getModelVoiveVotives().getModelOthers().size(); i++) {
                                if (!status.equals("all")) {
                                    if (response.body().getModelVoiveVotives().getModelOthers().get(i).getStatus().equals(status)) {
                                        ModelOthers modelOthers = response.body().getModelVoiveVotives().getModelOthers().get(i);
                                        modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOthers.getId()+"","other", null, modelOthers.getTitle(), modelOthers.getDescription(), modelOthers.getLocation(), modelOthers.getLatitude(), modelOthers.getLongitude(), modelOthers.getDate(), null, null, null, modelOthers.getStatus()));
                                    }
                                } else {
                                    ModelOthers modelOthers = response.body().getModelVoiveVotives().getModelOthers().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOthers.getId()+"","other", null, modelOthers.getTitle(), modelOthers.getDescription(), modelOthers.getLocation(), modelOthers.getLatitude(), modelOthers.getLongitude(), modelOthers.getDate(), null, null, null, modelOthers.getStatus()));
                                }
                            }
                        } else if (type.equals("online")) {
                            for (int i = 0; i < response.body().getModelVoiveVotives().getModelOnlines().size(); i++) {
                                if (!status.equals("all")) {
                                if (response.body().getModelVoiveVotives().getModelOnlines().get(i).getStatus().equals(status)) {
                                    ModelOnlines modelOnlines = response.body().getModelVoiveVotives().getModelOnlines().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOnlines.getId()+"","online", null, modelOnlines.getTitle(), modelOnlines.getDescription(), modelOnlines.getSite_name(), null, null, modelOnlines.getDate(), null, modelOnlines.getSite_url(), null, modelOnlines.getStatus()));
                                }
                            }else {
                                    ModelOnlines modelOnlines = response.body().getModelVoiveVotives().getModelOnlines().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOnlines.getId()+"","online", null, modelOnlines.getTitle(), modelOnlines.getDescription(), modelOnlines.getSite_name(), null, null, modelOnlines.getDate(), null, modelOnlines.getSite_url(), null, modelOnlines.getStatus()));

                                }
                                }
                        }
                    } else {
                        for (int i = 0; i < response.body().getModelVoiveVotives().getModelFoods().size(); i++) {
                            if (!status.equals("all")){
                                if (response.body().getModelVoiveVotives().getModelFoods().get(i).getStatus().equals(status)) {
                                    ModelFoods modelFoods = response.body().getModelVoiveVotives().getModelFoods().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelFoods.getId()+"","food", modelFoods.getFood_type(), modelFoods.getFood_name(), null, modelFoods.getLocation(), modelFoods.getLatitude(), modelFoods.getLongitude(), modelFoods.getDate(), null, null, modelFoods.getStart_at() + " الی " + modelFoods.getEnd_at(), modelFoods.getStatus()));
                                }
                            }else {
                                ModelFoods modelFoods = response.body().getModelVoiveVotives().getModelFoods().get(i);
                                modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelFoods.getId()+"","food",modelFoods.getFood_type(), modelFoods.getFood_name(), null, modelFoods.getLocation(), modelFoods.getLatitude(), modelFoods.getLongitude(), modelFoods.getDate(), null, null, modelFoods.getStart_at() + " الی " + modelFoods.getEnd_at(), modelFoods.getStatus()));
                            }

                        }
                        //1
                        for (int i = 0; i < response.body().getModelVoiveVotives().getModelBooks().size(); i++) {
                            if (!status.equals("all")) {
                                if (response.body().getModelVoiveVotives().getModelBooks().get(i).getStatus().equals(status)) {
                                    ModelBooks modelBooks = response.body().getModelVoiveVotives().getModelBooks().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelBooks.getId()+"","book",modelBooks.getBook_type(), modelBooks.getBook_name(), modelBooks.getDescription(), modelBooks.getLocation(), modelBooks.getLatitude(), modelBooks.getLongitude(), modelBooks.getDate(), modelBooks.getWriter(), null, modelBooks.getActive_time(), modelBooks.getStatus()));
                                }
                            } else {
                                ModelBooks modelBooks = response.body().getModelVoiveVotives().getModelBooks().get(i);
                                modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelBooks.getId()+"","book", modelBooks.getBook_type(), modelBooks.getBook_name(), modelBooks.getDescription(), modelBooks.getLocation(), modelBooks.getLatitude(), modelBooks.getLongitude(), modelBooks.getDate(), modelBooks.getWriter(), null, modelBooks.getActive_time(), modelBooks.getStatus()));

                            }
                        }
                        ///2
                        for (int i = 0; i < response.body().getModelVoiveVotives().getModelOthers().size(); i++) {
                            if (!status.equals("all")) {
                                if (response.body().getModelVoiveVotives().getModelOthers().get(i).getStatus().equals(status)) {
                                    ModelOthers modelOthers = response.body().getModelVoiveVotives().getModelOthers().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOthers.getId()+"","other", null, modelOthers.getTitle(), modelOthers.getDescription(), modelOthers.getLocation(), modelOthers.getLatitude(), modelOthers.getLongitude(), modelOthers.getDate(), null, null, null, modelOthers.getStatus()));
                                }
                            } else {
                                ModelOthers modelOthers = response.body().getModelVoiveVotives().getModelOthers().get(i);
                                modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOthers.getId()+"","other", null, modelOthers.getTitle(), modelOthers.getDescription(), modelOthers.getLocation(), modelOthers.getLatitude(), modelOthers.getLongitude(), modelOthers.getDate(), null, null, null, modelOthers.getStatus()));
                            }
                        }

//3
                        for (int i = 0; i < response.body().getModelVoiveVotives().getModelOnlines().size(); i++) {
                            if (!status.equals("all")) {
                                if (response.body().getModelVoiveVotives().getModelOnlines().get(i).getStatus().equals(status)) {
                                    ModelOnlines modelOnlines = response.body().getModelVoiveVotives().getModelOnlines().get(i);
                                    modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOnlines.getId()+"","online", null, modelOnlines.getTitle(), modelOnlines.getDescription(), modelOnlines.getSite_name(), null, null, modelOnlines.getDate(), null, modelOnlines.getSite_url(), null, modelOnlines.getStatus()));
                                }
                            }else {
                                ModelOnlines modelOnlines = response.body().getModelVoiveVotives().getModelOnlines().get(i);
                                modelAdapterShowAllNazris.add(new ModelAdapterShowAllNazri(modelOnlines.getId()+"","online", null, modelOnlines.getTitle(), modelOnlines.getDescription(), modelOnlines.getSite_name(), null, null, modelOnlines.getDate(), null, modelOnlines.getSite_url(), null, modelOnlines.getStatus()));

                            }
                        }
                    }
                    fdoshboardBinding.listNazri.setLayoutManager(new LinearLayoutManager(getContext()));
                    fdoshboardBinding.listNazri.setAdapter(new AdapterMyVotive(getContext(),modelAdapterShowAllNazris));

                }
           }

            @Override
            public void onFailure(Call<ModelVotive> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putString("type","all").apply();
        sharedPreferences.edit().putString("status","all").apply();
    }
}
