package mostafa.hafezypoor.nazri.fragment;



import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.databinding.FragmentFSplashSreenBinding;

public class FSplashSreen extends Fragment {
FragmentFSplashSreenBinding fragmentFSplashSreenBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            this.fragmentFSplashSreenBinding=DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_f_splash_sreen,container,false);

        return fragmentFSplashSreenBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(0);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame_layout,new FMain(),"FMain").commit();

                    }
                });
            }
        }).start();
    }
}