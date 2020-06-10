package ir.coleo.chayi.pipline.layers;

import org.json.JSONException;

import java.lang.reflect.Field;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.RequestType;
import ir.coleo.chayi.pipline.call_backs.FailReason;

import static ir.coleo.chayi.constats.Constants.NO_TOKEN;

public class TokenLayer extends NetworkLayer {

    private NetworkLayer tempNextLayer;
    private String TAG = getClass().getSimpleName();


    public TokenLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    private static <T> boolean needToken(Class<T> input, String function) {
        try {
            Field field;
            field = input.getField(function + "_token");
            return field.getBoolean(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean haveToken() {
        return !Constants.getToken().equals("Token " + Constants.NO_TOKEN);
    }

    @Override
    public NetworkData before(NetworkData data) {
        if (data.getRequestType() == RequestType.CUSTOM_POST) {
            data.setNeedToken(needToken(data.getInput(), data.getFunctionName()));
        } else {
            data.setNeedToken(true);
        }
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        if (data.isNeedToken()) {
            String token = Constants.getToken();
            if (token.equalsIgnoreCase("Token " + NO_TOKEN)) {
                data.addResult(TAG, true);
                tempNextLayer = nextLayer;
                nextLayer = null;
            } else {
                data.addResult(TAG, false);
                data.setToken(token);
            }
        } else {
            data.addResult(TAG, false);
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (data.getResult(TAG) && data.isHandled()) {
            data.setHandled(true);
            nextLayer = tempNextLayer;
            data.getCallBack().fail(FailReason.Token);
        } else {
            if (data.getResponse() != null) {
                try {
                    Constants.setToken(data.getResponse().getString("token"));
                } catch (JSONException ignore) {
                }
            }
        }
        return data;
    }
}
