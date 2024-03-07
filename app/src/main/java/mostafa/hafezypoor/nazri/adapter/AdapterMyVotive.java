package mostafa.hafezypoor.nazri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kotlin.jvm.internal.ShortCompanionObject;
import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.AdapterMyVotiveBinding;
import mostafa.hafezypoor.nazri.fragment.FShowDetailMMyNazri;
import mostafa.hafezypoor.nazri.fragment.FShowDetailNazri;
import mostafa.hafezypoor.nazri.model.ModelAdapterShowAllNazri;

public class AdapterMyVotive extends RecyclerView.Adapter<AdapterMyVotive.ViewHolder> {
    private Context context;
    private List<ModelAdapterShowAllNazri> modelAdapterShowAllNazri;

    public AdapterMyVotive(Context context, List<ModelAdapterShowAllNazri> modelAdapterShowAllNazri) {
        this.context = context;
        this.modelAdapterShowAllNazri = modelAdapterShowAllNazri;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.adapter_my_votive,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.adapterMyVotiveBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity= (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FShowDetailMMyNazri(modelAdapterShowAllNazri.get(position)),"fshowsddffAllNazri").commit();
            }
        });

        if (modelAdapterShowAllNazri.get(position).getType()!=null){
            if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("food")){
                holder.adapterMyVotiveBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
                holder.adapterMyVotiveBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getName());
                holder.adapterMyVotiveBinding.nazriDate.setText(modelAdapterShowAllNazri.get(position).getStart_end());
                if (modelAdapterShowAllNazri.get(position).getType().equals("dinner")){
                    holder.adapterMyVotiveBinding.nazriName.setText("نذر غذا برای شام");
                } else if (modelAdapterShowAllNazri.get(position).getType().equals("breakfast")) {
                    holder.adapterMyVotiveBinding.nazriName.setText("نذر غذا برای صبحانه");
                } else if (modelAdapterShowAllNazri.get(position).getType().equals("lunch")) {
                    holder.adapterMyVotiveBinding.nazriName.setText("نذر غذا برای ناهار");
                }
            } else if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("book")) {
                if (modelAdapterShowAllNazri.get(position).getType().equals("public")) {
                    holder.adapterMyVotiveBinding.nazriName.setText("نذر کتاب عمومی");
                    holder.adapterMyVotiveBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getDescription());
                    holder.adapterMyVotiveBinding.nazriDate.setText(modelAdapterShowAllNazri.get(position).getStart_end());
                    holder.adapterMyVotiveBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
                }
            }
        }else if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("other")) {
            holder.adapterMyVotiveBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
            holder.adapterMyVotiveBinding.nazriName.setText("نذر سایر");
            holder.adapterMyVotiveBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getDescription());

        }else if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("online")){
            holder.adapterMyVotiveBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
            holder.adapterMyVotiveBinding.nazriName.setText("نذر آنلاین");
            holder.adapterMyVotiveBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getDescription());
            holder.adapterMyVotiveBinding.nazriDate.setText(modelAdapterShowAllNazri.get(position).getSite());
        }
       if (modelAdapterShowAllNazri.get(position).getStatus().equals("Active")){
           holder.adapterMyVotiveBinding.textStatus.setText("منتشر شد");
       }else if (modelAdapterShowAllNazri.get(position).getStatus().equals("Pending")){
           holder.adapterMyVotiveBinding.textStatus.setText("در انتظار تایید");
       }else if (modelAdapterShowAllNazri.get(position).getStatus().equals("expire")){
           holder.adapterMyVotiveBinding.textStatus.setText("منقضی شد");
       }else if (modelAdapterShowAllNazri.get(position).getStatus().equals("rejected")){
           holder.adapterMyVotiveBinding.textStatus.setText("رد شد");
       }



    }

    @Override
    public int getItemCount() {
        return modelAdapterShowAllNazri.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
AdapterMyVotiveBinding adapterMyVotiveBinding;
        public ViewHolder(@NonNull AdapterMyVotiveBinding adapterMyVotiveBinding) {
            super(adapterMyVotiveBinding.getRoot());
            this.adapterMyVotiveBinding=adapterMyVotiveBinding;
        }
    }
}
