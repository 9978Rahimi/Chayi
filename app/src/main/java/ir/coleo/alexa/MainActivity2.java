package ir.coleo.alexa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ir.coleo.alexa.models.Citizen;
import ir.coleo.chayi.pipline.PipLine;
import ir.coleo.chayi.pipline.SimpleIdlingResource;
import ir.coleo.chayi.pipline.call_backs.FailReason;
import ir.coleo.chayi.pipline.call_backs.UserCallBackSingle;

public class MainActivity2 extends TestActivity {
    EditText codeInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String phone = (String) getIntent().getExtras().get("phone");

        Button button;
        button = findViewById(R.id.code_button);
        button.setOnClickListener(view -> putName(phone));
        codeInput = findViewById(R.id.code_edit_text);
        codeInput.setText(phone);

    }


    public void putName(String phone) {
        PipLine.request("app_check_code", Citizen.class, new UserCallBackSingle<Citizen>() {
            @Override
            public void success(Citizen citizen) {
                mIdlingResource.setIdleState(true);
            }

            @Override
            public void fail(FailReason reason) {
                ((SimpleIdlingResource) getIdlingResource()).setIdleState(true);
            }
        }, phone, codeInput.getText().toString(), "");
    }


}