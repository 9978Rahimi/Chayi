package ir.coleo.chayi.pipline.layers;

import java.lang.reflect.Field;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.RequestType;

import static ir.coleo.chayi.constats.Constants.NO_TOKEN;

public class TokenLayer extends NetworkLayer {

    private NetworkLayer tempNextLayer;
    private boolean tokenFail = false;

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
                tokenFail = true;
                tempNextLayer = nextLayer;
                nextLayer = null;
            } else {
                tokenFail = false;
                data.setToken(token);
            }
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (tokenFail) {
            nextLayer = tempNextLayer;
            data.getCallBack().fail();
        }
        return data;
    }
}