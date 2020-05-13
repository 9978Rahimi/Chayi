package ir.coleo.alexa;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ir.coleo.alexa.testing_models.Citizen;
import ir.coleo.alexa.testing_models.CitizenAppVersion;
import ir.coleo.alexa.testing_models.User;
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
            Log.i(TAG, "onCreate: " + Citizen.create_request(null,"sadsadsad").toString());
        });
    }

    private static String TAG = "TAG";

    public void putName() {

    }


}