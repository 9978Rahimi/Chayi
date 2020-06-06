package ir.coleo.chayi.pipline.layers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import ir.coleo.chayi.pipline.NetworkData;

import static ir.coleo.chayi.pipline.layers.UrlLayer.getObjectName;

public class ResponseLayer extends NetworkLayer {

    public ResponseLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        return data;
    }

    private static <T extends Chayi> ArrayList<T> multiParse(Class<?> input, JSONObject response) {
        try {
            JSONArray array = response.getJSONArray(UrlLayer.getAllUrl(input));
            ArrayList<T> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                list.add(RetrofitSingleTone.getInstance().getGson().fromJson(array.getJSONObject(i).toString(), (Type) input));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static <T extends Chayi> T singleParse(NetworkData data) {
        return RetrofitSingleTone.getInstance().getGson().fromJson(data.getResponse().toString(), (Type) data.getInput());
    }

    private static <T extends Chayi> T responseParserPutOrPost(Class<?> input, JSONObject response) {
        try {
            return RetrofitSingleTone.getInstance().getGson()
                    .fromJson(response.get(getObjectName(input)).toString(), (Type) input);
        } catch (JSONException | NullPointerException ignore) {
        }
        return null;
    }

    @Override
    public NetworkData work(NetworkData data) {
        switch (data.getRequestType()) {
            case GET:
                if (data.isSingle()) {
                    data.getCallBack().success(singleParse(data));
                } else {
                    data.getCallBack().success(multiParse(data.getInput(), data.getResponse()));
                }
                break;
            case PUT:
            case POST:
            case DELETE:
            case CUSTOM_POST:
                data.getCallBack().success(responseParserPutOrPost(data.getInput(), data.getResponse()));
                break;
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        return data;
    }
}
