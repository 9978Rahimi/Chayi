package ir.coleo.alexa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ir.coleo.alexa.models.Citizen;
import ir.coleo.alexa.models.android.HistoryRequest;
import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.PipLine;
import ir.coleo.chayi.pipline.RequestType;
import ir.coleo.chayi.pipline.UserCallBack;

public class MainActivity extends AppCompatActivity {

    private EditText textView;
    private Button button;
    private Button code;
    private HistoryRequest request;

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

            PipLine.request(RequestType.POST, Citizen.class, false, new UserCallBack<ArrayList<Citizen>>() {
                @Override
                public void success(ArrayList<Citizen> citizens) {

                }

                @Override
                public void fail() {

                }
            });

            PipLine.request("app_enter", Citizen.class, true, new UserCallBack<Citizen>() {

                @Override
                public void success(Citizen citizen) {

                }

                @Override
                public void fail() {

                }
            }, "09384142925");

            Citizen citizen = new Citizen();
            PipLine.request(RequestType.GET, citizen, true, new UserCallBack<Citizen>() {
                @Override
                public void success(Citizen citizen) {

                }

                @Override
                public void fail() {

                }
            });

//            request = new HistoryRequest(25);
//            Chayi.customPostRequest(new SingleChayiCallBackOne<HistoryRequest>() {
//
//                @Override
//                public void onResponse(HistoryRequest historyRequest) {
//                    Log.i("TAG", "onResponse: finish");
//                }
//
//                @Override
//                public void fail(String errorMassage) {
//                    Log.i("TAG", "fail: finish");
//                }
//            }, "driver_weighting", request, new ArrayList<>());

//            Retrofit retrofit =
//                    new Retrofit.Builder()
//                            .baseUrl("http://httpbin.org")
//                            .addCallAdapterFactory(new ErrorHandlingAdapter.ErrorHandlingCallAdapterFactory())
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//
//            ErrorHandlingAdapter.HttpBinService service = retrofit.create(ErrorHandlingAdapter.HttpBinService.class);
//            ErrorHandlingAdapter.MyCall<ErrorHandlingAdapter.Ip> ip = service.getIp();
//            ip.enqueue(
//                    new ErrorHandlingAdapter.MyCallback<ErrorHandlingAdapter.Ip>() {
//                        @Override
//                        public void success(Response<ErrorHandlingAdapter.Ip> response) {
//                            System.out.println("SUCCESS! " + response.body().origin);
//                        }
//
//                        @Override
//                        public void unauthenticated(Response<?> response) {
//                            System.out.println("UNAUTHENTICATED");
//                        }
//
//                        @Override
//                        public void clientError(Response<?> response) {
//                            System.out.println("CLIENT ERROR " + response.code() + " " + response.message());
//                        }
//
//                        @Override
//                        public void serverError(Response<?> response) {
//                            System.out.println("SERVER ERROR " + response.code() + " " + response.message());
//                        }
//
//                        @Override
//                        public void networkError(IOException e) {
//                            System.err.println("NETWORK ERROR " + e.getMessage());
//                        }
//
//                        @Override
//                        public void unexpectedError(Throwable t) {
//                            System.err.println("FATAL ERROR " + t.getMessage());
//                        }
//                    });
        });
    }

    private static String TAG = "TAG";

    public void putName() {

    }


}