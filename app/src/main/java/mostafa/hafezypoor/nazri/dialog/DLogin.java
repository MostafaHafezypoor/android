package mostafa.hafezypoor.nazri.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import mostafa.hafezypoor.nazri.R;
import mostafa.hafezypoor.nazri.model.ModelVerifyManager;
import mostafa.hafezypoor.nazri.server.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DLogin extends Dialog {

    private Context context;
    private ViewFlipper viewFlipper;
    private TextInputEditText number;
    private SharedPreferences sharedPreferences;
    public DLogin(@NonNull Context context) {
        super(context);
        this.context=context;
        sharedPreferences= context.getSharedPreferences("save",Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlogin);
        viewFlipper=findViewById(R.id.viewFlipperDialog);
        login();

    }
    private void login(){
       number=findViewById(R.id.number);
        Button btnLogin=findViewById(R.id.btnLogin);
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           if (charSequence.length()==11){
               btnLogin.setBackgroundColor(context.getColor(R.color.green));
               btnLogin.setEnabled(true);
           }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitInit.getInstance().login(number.getText().toString().trim()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            viewFlipper.showNext();
                            EditTextOtp  editTextOtp=findViewById(R.id.EditTextOtp);
                            MaterialTextView textShowNumber=findViewById(R.id.textShowNumber);
                            String numberShow=" کدی که برای "+number.getText().toString().trim() + " ارسال شده است را وارد کنید ";
                            textShowNumber.setText(numberShow);
                            Button btnCode=findViewById(R.id.btnCode);
                            btnCode.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    RetrofitInit.getInstance().verify(number.getText().toString().trim(),editTextOtp.getOtp()).enqueue(new Callback<ModelVerifyManager>() {
                                        @Override
                                        public void onResponse(Call<ModelVerifyManager> call, Response<ModelVerifyManager> response) {
                                            if (response.isSuccessful()){
                                             sharedPreferences.edit().putString("token",response.body().getUser().getToken()).apply();
                                                Toast.makeText(getContext(), "خوش آمدید", Toast.LENGTH_SHORT).show();
                                             dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ModelVerifyManager> call, Throwable t) {

                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }
}
