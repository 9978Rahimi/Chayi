package ir.coleo.chayi.pipline.layers;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.responseUtil.Error;
import ir.hatamiarash.toast.RTLToast;

public class HandleErrorLayer extends NetworkLayer {

    private NetworkLayer tempNetworkLayer;
    private boolean fail = false;

    public HandleErrorLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        int code = data.getBodyResponse().code();
        if (code >= 100 && code < 400) {
            fail = false;
            if (data.getBodyResponse().body() != null) {
                try {
                    data.setResponse(new JSONObject(data.getBodyResponse().body().string()));
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            fail = true;
            tempNetworkLayer = nextLayer;
            nextLayer = null;
            Error error = null;
            if (data.getBodyResponse().errorBody() != null) {
                try {
                    error = RetrofitSingleTone
                            .getInstance()
                            .getGson()
                            .fromJson(data.getBodyResponse().errorBody().string(), Error.class);
                } catch (IOException | JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
            if (error != null) {
                if (code == 403) {
                    if (TokenLayer.haveToken()) {
                        Error finalError = error;
                        ((Activity) Constants.context).runOnUiThread(() -> {
                            RTLToast.error(Constants.context, finalError.getErrors(), Toast.LENGTH_LONG).show();
                            Constants.setToken(Constants.NO_TOKEN);
                            Intent intent = new Intent(Constants.context, Constants.getRestartActivity());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Constants.context.startActivity(intent);
                            ((Activity) Constants.context).finish();
                        });

                    }
                } else {
                    RTLToast.error(Constants.context, error.getErrors(), Toast.LENGTH_LONG).show();
                }
            }
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (fail) {
            nextLayer = tempNetworkLayer;
            data.getCallBack().fail();
        }

        return data;
    }
}
