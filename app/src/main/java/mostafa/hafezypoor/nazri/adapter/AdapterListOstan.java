package mostafa.hafezypoor.nazri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.AdapterListOstanBinding;
import mostafa.hafezypoor.nazri.model.ModelOstan;
import mostafa.hafezypoor.nazri.model.ModelOstanState;

public class AdapterListOstan extends RecyclerView.Adapter<AdapterListOstan.ViewHolder> {

    private Context context;
    private ModelOstan modelOstan;

    public AdapterListOstan(Context context, ModelOstan modelOstan) {
        this.context = context;
        this.modelOstan = modelOstan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_list_ostan,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.adapterListOstanBinding.ostanName.setText(modelOstan.getModelOstanStates().get(position).getName());
holder.adapterListOstanBinding.ostanName.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

            onstanClickManager.onSelectOstan(modelOstan.getModelOstanStates().get(position));

    }
});
    }

    @Override
    public int getItemCount() {
        return  modelOstan.getModelOstanStates().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
AdapterListOstanBinding adapterListOstanBinding;
        public ViewHolder(@NonNull AdapterListOstanBinding adapterListOstanBinding) {
            super(adapterListOstanBinding.getRoot());
            this.adapterListOstanBinding=adapterListOstanBinding;
        }
    }
    private OnstanClickManager onstanClickManager;
   public interface OnstanClickManager{
      public   void onSelectOstan(ModelOstanState modelOstanState);
    }
    public void setOnstanClickManager(OnstanClickManager onstanClickManager){
        this.onstanClickManager=onstanClickManager;
    }
}
