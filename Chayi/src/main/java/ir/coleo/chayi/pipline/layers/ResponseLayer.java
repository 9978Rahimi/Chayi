package ir.coleo.chayi.pipline.layers;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.call_backs.FailReason;
import ir.coleo.chayi.pipline.call_backs.UserCallBackArray;
import ir.coleo.chayi.pipline.call_backs.UserCallBackSingle;
import ir.coleo.chayi.pipline.call_backs.UserCallBackSingleStatus;

import static ir.coleo.chayi.pipline.layers.UrlLayer.getObjectName;

public class ResponseLayer extends NetworkLayer {

    public ResponseLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        return data;
    }

    private static <T> ArrayList<T> multiParse(Class<T> input, JSONObject response) {
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

    private static <T> T singleParse(NetworkData data) {
        try {
            return RetrofitSingleTone.getInstance().getGson().fromJson(data.getResponse().getJSONObject(getObjectName(data.getInput())).toString(), (Type) data.getInput());
        } catch (JSONException ignore) {
        }
        return null;
    }

    private static <T> T responseParserPutOrPost(Class<?> input, JSONObject response) {
        try {
            return RetrofitSingleTone.getInstance().getGson()
                    .fromJson(response.get(getObjectName(input)).toString(), (Type) input);
        } catch (JSONException | NullPointerException ignore) {
        }
        return null;
    }

    @Override
    public NetworkData work(NetworkData data) {
        ((Activity) Constants.context).runOnUiThread(() -> {
            if (data.isHandled())
                switch (data.getRequestType()) {
                    case GET:
                        if (data.isSingle()) {
                            if (data.getCallBack() instanceof UserCallBackSingle) {
                                ((UserCallBackSingle) data.getCallBack()).success(singleParse(data));
                                data.setHandled(true);
                            } else if (data.getCallBack() instanceof UserCallBackSingleStatus) {
                                ((UserCallBackSingleStatus) data.getCallBack()).success(singleParse(data), data.getBodyResponse().code());
                                data.setHandled(true);
                            }
                        } else {
                            if (data.getCallBack() instanceof UserCallBackArray) {
                                ((UserCallBackArray) data.getCallBack()).success(multiParse(data.getInput(), data.getResponse()));
                                data.setHandled(true);
                            }
                        }
                        break;
                    case PUT:
                    case POST:
                    case DELETE:
                    case CUSTOM_POST:
                        if (data.isSingle()) {
                            if (data.getCallBack() instanceof UserCallBackSingle) {
                                ((UserCallBackSingle) data.getCallBack()).success(responseParserPutOrPost(data.getInput(), data.getResponse()));
                                data.setHandled(true);
                            } else if (data.getCallBack() instanceof UserCallBackSingleStatus) {
                                ((UserCallBackSingleStatus) data.getCallBack()).success(responseParserPutOrPost(data.getInput(), data.getResponse()), data.getBodyResponse().code());
                                data.setHandled(true);
                            }
                        } else {
                            if (data.getCallBack() instanceof UserCallBackArray) {
                                ((UserCallBackArray) data.getCallBack()).success(multiParse(data.getInput(), data.getResponse()));
                                data.setHandled(true);
                            }
                        }
                        break;
                }
            if (data.isHandled()) {
                data.getCallBack().fail(FailReason.Unknown);
            }
        });

        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        return data;
    }
}
