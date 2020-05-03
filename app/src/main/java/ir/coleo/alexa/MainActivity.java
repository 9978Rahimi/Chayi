package ir.coleo.alexa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText textView;
    private Button button;
    private Button code;
//    Citizen citizen = new Citizen("09384142925");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Constants.setBase_url("https://dev.zimaapp.ir/api/av1/");
//        Constants.context = getApplicationContext();

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        code = findViewById(R.id.code_button);

        button.setOnClickListener(view -> putName());
        code.setOnClickListener(v -> {

        });
    }

    private static String TAG = "TAG";

    public void putName() {
      
    }


}