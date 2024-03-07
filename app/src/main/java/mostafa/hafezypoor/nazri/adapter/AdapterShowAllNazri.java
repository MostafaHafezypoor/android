package mostafa.hafezypoor.nazri.adapter;

import android.app.Activity;
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

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.AdapterShowAllNazriBinding;
import mostafa.hafezypoor.nazri.fragment.FMain;
import mostafa.hafezypoor.nazri.fragment.FShowDetailNazri;
import mostafa.hafezypoor.nazri.model.ModelAdapterShowAllNazri;
import mostafa.hafezypoor.nazri.model.ModelVotive;

public class AdapterShowAllNazri extends RecyclerView.Adapter<AdapterShowAllNazri.ViewHolder> {
    private Context context;
    private List<ModelAdapterShowAllNazri> modelAdapterShowAllNazri;

    public AdapterShowAllNazri(Context context, List<ModelAdapterShowAllNazri> modelAdapterShowAllNazri) {
        this.context = context;
        this.modelAdapterShowAllNazri = modelAdapterShowAllNazri;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_show_all_nazri,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.adapterShowAllNazriBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity= (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FShowDetailNazri(modelAdapterShowAllNazri.get(position)),"fshowAllNazri").commit();
            }
        });

  if (modelAdapterShowAllNazri.get(position).getType()!=null){
      if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("food")){
          holder.adapterShowAllNazriBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
          holder.adapterShowAllNazriBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getName());
          holder.adapterShowAllNazriBinding.nazriDate.setText(modelAdapterShowAllNazri.get(position).getStart_end());
          if (modelAdapterShowAllNazri.get(position).getType().equals("dinner")){
              holder.adapterShowAllNazriBinding.nazriName.setText("نذر غذا برای شام");
          } else if (modelAdapterShowAllNazri.get(position).getType().equals("breakfast")) {
              holder.adapterShowAllNazriBinding.nazriName.setText("نذر غذا برای صبحانه");
          } else if (modelAdapterShowAllNazri.get(position).getType().equals("lunch")) {
              holder.adapterShowAllNazriBinding.nazriName.setText("نذر غذا برای ناهار");
          }
      } else if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("book")) {
          if (modelAdapterShowAllNazri.get(position).getType().equals("public")) {
              holder.adapterShowAllNazriBinding.nazriName.setText("نذر کتاب عمومی");
              holder.adapterShowAllNazriBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getDescription());
              holder.adapterShowAllNazriBinding.nazriDate.setText(modelAdapterShowAllNazri.get(position).getStart_end());
              holder.adapterShowAllNazriBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
          }
      }
  }else if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("other")) {
      holder.adapterShowAllNazriBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
      holder.adapterShowAllNazriBinding.nazriName.setText("نذر سایر");
      holder.adapterShowAllNazriBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getDescription());

  }else if (modelAdapterShowAllNazri.get(position).getTypeVotive().equals("online")){
      holder.adapterShowAllNazriBinding.nazriDatePublish.setText(modelAdapterShowAllNazri.get(position).getDate());
      holder.adapterShowAllNazriBinding.nazriName.setText("نذر آنلاین");
      holder.adapterShowAllNazriBinding.nazriDiscription.setText(modelAdapterShowAllNazri.get(position).getDescription());
      holder.adapterShowAllNazriBinding.nazriDate.setText(modelAdapterShowAllNazri.get(position).getSite());
  }



    }

    @Override
    public int getItemCount() {

        return modelAdapterShowAllNazri.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        AdapterShowAllNazriBinding adapterShowAllNazriBinding;
        public ViewHolder(@NonNull AdapterShowAllNazriBinding adapterShowAllNazriBinding) {
            super(adapterShowAllNazriBinding.getRoot());
            this.adapterShowAllNazriBinding=adapterShowAllNazriBinding;
        }
    }
}
