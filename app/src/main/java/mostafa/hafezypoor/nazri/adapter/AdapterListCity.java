package mostafa.hafezypoor.nazri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.AdapterCityListBinding;
import mostafa.hafezypoor.nazri.model.ModelCities;
import mostafa.hafezypoor.nazri.model.ModelCitiesManage;
import mostafa.hafezypoor.nazri.model.ModelOstanState;

public class AdapterListCity extends RecyclerView.Adapter<AdapterListCity.ViewHolder> {
    private AdapterCityListBinding adapterCityListBinding;
    private Context context;

 private List<ModelCities> modelCities;

    public AdapterListCity(Context context, List<ModelCities> modelCities) {
        this.context = context;
        this.modelCities = modelCities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_city_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.adapterCityListBinding.cityName.setText(modelCities.get(position).getName());
      holder.adapterCityListBinding.cityName.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            onCityClickManager.onSelectCity(modelCities.get(position));
          }
      });

    }

    @Override
    public int getItemCount() {
        return modelCities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        AdapterCityListBinding adapterCityListBinding;
        public ViewHolder(@NonNull AdapterCityListBinding adapterCityListBinding) {
            super(adapterCityListBinding.getRoot());
            this.adapterCityListBinding=adapterCityListBinding;
        }

    }
    private OnCityClickManager onCityClickManager;
    public interface OnCityClickManager{
        public void onSelectCity(ModelCities modelCities);
    }
    public void setOnstanClickManager(OnCityClickManager onCityClickManager){
        this.onCityClickManager=onCityClickManager;
    }
}
