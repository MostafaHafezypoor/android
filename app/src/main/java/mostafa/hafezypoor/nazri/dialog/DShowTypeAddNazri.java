package mostafa.hafezypoor.nazri.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.fragment.FAddNazriBook;
import mostafa.hafezypoor.nazri.fragment.FAddNazriFood;
import mostafa.hafezypoor.nazri.fragment.FAddNazriOnline;
import mostafa.hafezypoor.nazri.fragment.FAddNazriOther;

public class DShowTypeAddNazri extends Dialog {
    private AppCompatActivity appCompatActivity;
    public DShowTypeAddNazri(@NonNull Context context) {
        super(context);
        appCompatActivity= (AppCompatActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dshow_type_add_nazri);
        MaterialTextView btnFood=findViewById(R.id.btnFood);
        MaterialTextView btnBook=findViewById(R.id.btnBook);
        MaterialTextView btnOnline=findViewById(R.id.btnOnline);
        MaterialTextView btnOther=findViewById(R.id.btnOther);
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               appCompatActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FAddNazriFood(),"faddFood").commit();
                 dismiss();
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appCompatActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FAddNazriBook(),"faddaaaff").commit();
                dismiss();
            }
        });
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appCompatActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FAddNazriOther(),"adssdadaa").commit();
                dismiss();
            }
        });
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appCompatActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.main_activity_frame_layout,new FAddNazriOnline(),"ffaddonlie").commit();
                dismiss();
            }
        });


    }
}
