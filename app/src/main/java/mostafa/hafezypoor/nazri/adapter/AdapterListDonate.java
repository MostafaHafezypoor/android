package mostafa.hafezypoor.nazri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.AdapterListDonateBinding;
import mostafa.hafezypoor.nazri.model.ModelDonate;

public class AdapterListDonate extends RecyclerView.Adapter<AdapterListDonate.ViewHolder> {
private Context context;
private ModelDonate modelDonate;

    public AdapterListDonate(Context context, ModelDonate modelDonate) {
        this.context = context;
        this.modelDonate = modelDonate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_list_donate,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.adapterListDonateBinding.textDonate.setText(modelDonate.getDonate().get(position).getAmount()+modelDonate.getDonate().get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return modelDonate.getDonate().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
AdapterListDonateBinding adapterListDonateBinding;
        public ViewHolder(@NonNull AdapterListDonateBinding adapterListDonateBinding) {
            super(adapterListDonateBinding.getRoot());
           this.adapterListDonateBinding=adapterListDonateBinding;
        }
    }
}
