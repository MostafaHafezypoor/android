package mostafa.hafezypoor.nazri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.AdapterNazrOnlineBinding;
import mostafa.hafezypoor.nazri.fragment.FShowDetailNazri;
import mostafa.hafezypoor.nazri.model.ModelAdapterShowAllNazri;
import mostafa.hafezypoor.nazri.model.ModelBooks;
import mostafa.hafezypoor.nazri.model.ModelOnlines;

public class AdapterNazrOnline extends RecyclerView.Adapter<AdapterNazrOnline.ViewHolder>{
    private Context context;
    private List<ModelOnlines>modelOnlines;

    public AdapterNazrOnline(Context context, List<ModelOnlines> modelOnlines) {
        this.context = context;
        this.modelOnlines = modelOnlines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_nazr_online,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelOnlines modelOnlines1=modelOnlines.get(position);
        holder.adapterNazrOnlineBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity= (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FShowDetailNazri(new ModelAdapterShowAllNazri(modelOnlines1.getId()+"","online",modelOnlines1.getDescription(),modelOnlines1.getTitle(),modelOnlines1
                        .getDescription(),modelOnlines1.getSite_name(),null,null,modelOnlines1.getDate(),null,modelOnlines1.getSite_url(),modelOnlines1.getDate(),null)),"fshowAllNadaddzri").commit();
            }
        });
      holder.adapterNazrOnlineBinding.nameNazrOnline.setText(modelOnlines.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return modelOnlines.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
AdapterNazrOnlineBinding adapterNazrOnlineBinding;
        public ViewHolder(@NonNull AdapterNazrOnlineBinding adapterNazrOnlineBinding) {
            super(adapterNazrOnlineBinding.getRoot());
            this.adapterNazrOnlineBinding=adapterNazrOnlineBinding;
        }
    }
}
