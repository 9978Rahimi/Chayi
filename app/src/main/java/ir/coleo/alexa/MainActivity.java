package ir.coleo.alexa;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ir.coleo.alexa.models.Citizen;
import ir.coleo.alexa.models.android.HistoryRequest;
import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.PipLine;
import ir.coleo.chayi.pipline.call_backs.FailReason;
import ir.coleo.chayi.pipline.call_backs.UserCallBackSingle;

public class MainActivity extends AppCompatActivity {

    private EditText textView;
    private Button button;
    private Button code;
    private HistoryRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.setBase_url("https://dev0.zimaapp.ir/api/av1/");
        Constants.context = this;
        PipLine.setDebug(false);

        textView = findViewById(R.id.phone_textView);
        code = findViewById(R.id.phone_button);

        code.setOnClickListener(v -> {

//            Intent intent = new Intent(getApplication(), MainActivity2.class);
//            intent.putExtra("phone", textView.getText().toString());
//            startActivity(intent);
//
            for (int i = 0; i < 1000; i++) {
                final long start = System.currentTimeMillis();
                Log.i(TAG, "onCreate: start Time number " + i + " " + start);
                int finalI = i;
                PipLine.request("app_enter", Citizen.class, new UserCallBackSingle<Citizen>() {

                    @Override
                    public void fail(FailReason reason) {

                    }

                    @Override
                    public void success(Citizen citizen) {
                        Log.i(TAG, "onCreate: start Time number " + finalI + " toke " + (System.currentTimeMillis() - start));
                    }

                }, textView.getText().toString());

            }

        });
    }

    private static String TAG = "TAG";


}