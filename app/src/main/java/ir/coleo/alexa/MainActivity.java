package ir.coleo.alexa;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ir.coleo.alexa.chayi.Chayi;
import ir.coleo.alexa.chayi.callBack.ChayiCallBack;
import ir.coleo.alexa.chayi.callBack.SingleChayiCallBack;
import ir.coleo.alexa.chayi.constats.RetrofitSingleTone;
import ir.coleo.alexa.chayi.testing_models.Todo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(view -> putName());

    }

    private static String TAG = "TAG";

    public void putName() {
        Todo todo = new Todo(3, false, "nice work");
//        Call<ResponseBody> call = RetrofitSingleTone.getInstance().getChayiInterface().put(todo.getUrl(), "{todo}");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.i(TAG, "onResponse: ");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.i(TAG, "onFailure: ");
//            }
//        });

        todo.putRequest(new SingleChayiCallBack() {
            @Override
            public void onResponse(Chayi chayi) {
                Log.i(TAG, "onResponse: ");
            }

            @Override
            public void fail(String errorMassage) {
                Log.i(TAG, "fail: ");
            }
        });


    }

    public void showMyName() {
        Chayi.getAllRequest(Todo.class, new ChayiCallBack() {

            @Override
            public void onResponse(ArrayList<Chayi> chayis) {
                Chayi chayi = chayis.get(123);
                chayi.getRequest(new SingleChayiCallBack() {

                    @Override
                    public void onResponse(Chayi chayi) {
                        Log.i("MAIN_ACTIVITY", "onResponse: " + chayi.toString());
                    }

                    @Override
                    public void fail(String errorMassage) {

                    }
                });

            }

            @Override
            public void fail(String errorMassage) {

            }
        });
    }

}