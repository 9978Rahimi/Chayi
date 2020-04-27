package ir.coleo.alexa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ir.coleo.alexa.chayi.Chayi;
import ir.coleo.alexa.chayi.callBack.ChayiCallBack;
import ir.coleo.alexa.chayi.testing_models.Todo;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(view -> showMyName());

    }

    public void showMyName() {
        Chayi.getAllRequest(Todo.class, new ChayiCallBack() {

            @Override
            public void onResponse(ArrayList<Chayi> chayis) {

            }

            @Override
            public void fail(String errorMassage) {

            }
        });

    }

}