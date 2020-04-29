package ir.coleo.alexa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ir.coleo.alexa.chayi.Chayi;
import ir.coleo.alexa.chayi.callBack.SingleChayiCallBack;
import ir.coleo.alexa.chayi.constats.Constants;
import ir.coleo.alexa.chayi.testing_models.Citizen;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.setBase_url("https://dev.zimaapp.ir/api/av1/");

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(view -> putName());

    }

    private static String TAG = "TAG";

    public void putName() {
        Citizen citizen = new Citizen("09384142925");
        Chayi.customPostRequest(new SingleChayiCallBack() {
            @Override
            public void onResponse(Chayi chayi) {

            }

            @Override
            public void fail(String errorMassage) {

            }
        }, "app_enter", Citizen.class);
    }


}