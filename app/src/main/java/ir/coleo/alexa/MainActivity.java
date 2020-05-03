package ir.coleo.alexa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ir.coleo.alexa.chayi.Chayi;
import ir.coleo.alexa.chayi.callBack.SingleChayiCallBack;
import ir.coleo.alexa.chayi.constats.Constants;
import ir.coleo.alexa.chayi.testing_models.Citizen;

public class MainActivity extends AppCompatActivity {

    private EditText textView;
    private Button button;
    private Button code;
    Citizen citizen = new Citizen("09384142925");

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
            citizen.setCode(textView.getText().toString());
            citizen.customPostRequest(new SingleChayiCallBack() {
                @Override
                public void onResponse(Chayi chayi) {

                }

                @Override
                public void fail(String errorMassage) {

                }
            }, "app_check_code", Citizen.class);
        });
    }

    private static String TAG = "TAG";

    public void putName() {
        citizen.customPostRequest(new SingleChayiCallBack() {
            @Override
            public void onResponse(Chayi chayi) {

            }

            @Override
            public void fail(String errorMassage) {

            }
        }, "app_enter", Citizen.class);
    }


}