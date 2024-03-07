package mostafa.hafezypoor.nazri.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import mostafa.hafezypoor.nazri.R;

public class DExit extends Dialog {
    private SharedPreferences sharedPreferences;
    public DExit(@NonNull Context context) {
        super(context);
        sharedPreferences=context.getSharedPreferences("save",Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dexit);
        Button btnExit=findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("token",null).apply();
                dismiss();
            }
        });
    }
}
