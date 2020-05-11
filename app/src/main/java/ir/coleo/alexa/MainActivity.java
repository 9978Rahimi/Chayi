package ir.coleo.alexa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ir.coleo.alexa.testing_models.Citizen;
import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.callBack.SingleChayiCallBack;
import ir.coleo.chayi.constats.Constants;

public class MainActivity extends AppCompatActivity {

    private EditText textView;
    private Button button;
    private Button code;
//    Citizen citizen = new Citizen("09384142925");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.setBase_url("https://dev.zimaapp.ir/api/av1/");
        Constants.context = getApplicationContext();

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        code = findViewById(R.id.code_button);

        button.setOnClickListener(view -> putName());
        code.setOnClickListener(v -> {
            Chayi.customPostRequest(new SingleChayiCallBack() {
                @Override
                public void onResponse(Chayi chayi) {

                }

                @Override
                public void fail(String errorMassage) {

                }
            }, "app_enter", Citizen.class, false, "09384142925");
        });
    }

    private static String TAG = "TAG";

    public void putName() {

    }


}