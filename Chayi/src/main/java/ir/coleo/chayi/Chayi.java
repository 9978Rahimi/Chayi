package ir.coleo.chayi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ir.coleo.alexa.chayi.callBack.ChayiCallBack;
import ir.coleo.alexa.chayi.callBack.SingleChayiCallBack;
import ir.coleo.alexa.chayi.constats.ChayiInterface;
import ir.coleo.alexa.chayi.constats.Constants;
import ir.coleo.alexa.chayi.constats.RetrofitSingleTone;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class Chayi {

    private static String TAG = "chayi";

    protected int id;

    protected int getId() {
        return this.id;
    }

    private static String getAllUrl(Class<?> input) {
        Field allUrl;
        try {
            allUrl = input.getField("urlAll");
            return (String) allUrl.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract String getUrl();

    public abstract RequestBody getJsonObject();

    private RequestBody getCustomRequestBody(Class<?> input, String function) {
        Method method;
        try {
            method = input.getMethod(function);
            Object output = method.invoke(this);
            if (output instanceof RequestBody)
                return (RequestBody) output;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    /**
     * @param function name of api function
     * @apiNote target class that you are calling function on
     * it should have method with that function name
     * that return RequestBody type for post request
     */
    public void customPostRequest(SingleChayiCallBack callBack, String function, Class<?> input, boolean token) {
        String url = getAllUrl(input) + "/" + function;
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        if (token)
            repos = chayiInterface.post(url, getCustomRequestBody(input, function), Constants.getToken());
        else
            repos = chayiInterface.post(url, getCustomRequestBody(input, function));

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (handleResponse(response)) {
                    Log.i(TAG, "onResponse: body =" + response.body());
                    callBack.onResponse(null);
                } else {
                    callBack.fail("");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void putRequest(SingleChayiCallBack callBack, boolean token) {
        String url = getUrl();
        Log.i(TAG, "putRequest: url = " + url);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        if (token)
            repos = chayiInterface.put(url, this.getJsonObject(), Constants.getToken());
        else
            repos = chayiInterface.put(url, this.getJsonObject());


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (handleResponse(response)) {
                    callBack.onResponse(null);
                } else {
                    callBack.fail("");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void getRequest(SingleChayiCallBack callBack, boolean token) {
        String url = getUrl();
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        if (token)
            repos = chayiInterface.get(url, Constants.getToken());
        else
            repos = chayiInterface.get(url);


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    if (handleResponse(response)) {
                        Chayi newObject = RetrofitSingleTone.getInstance().getGson()
                                .fromJson(response.body().string(), Chayi.this.getClass());

                        callBack.onResponse(newObject);
                    } else {
                        callBack.fail("");//todo
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void getAllRequest(Class<?> input, ChayiCallBack chayiCallBack, boolean token) {
        String url = getAllUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        if (token)
            repos = chayiInterface.get(url, Constants.getToken());
        else
            repos = chayiInterface.get(url);

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    if (handleResponse(response)) {
                        ArrayList<Chayi> chayis = Chayi.AllResponseParser(input, response.body().string());
                        for (Chayi temp : chayis) {
                            Log.i(TAG, "onResponse: " + temp.toString());
                        }
                        chayiCallBack.onResponse(chayis);
                    } else {
                        chayiCallBack.fail("");
                        //todo

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private static ArrayList<Chayi> AllResponseParser(Class<?> input, String response) {
        try {
            JSONArray array = new JSONArray(response);
            ArrayList<Chayi> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                if (i == 0) {
                    Log.i(TAG, "AllResponseParser: " + array.getJSONObject(i).toString());
                }
                list.add(responseParser(input, array.getJSONObject(i).toString()));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static Chayi responseParser(Class<?> input, String response) {
        return (Chayi) RetrofitSingleTone.getInstance().getGson().fromJson(response, input);
    }

    private static boolean handleResponse(Response<ResponseBody> response) {
        if (response.code() >= 100 && response.code() < 400) {
            if (response.body() != null) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    Constants.setToken(object.getString("token"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            Log.i(TAG, "handleResponse: code = " + response.code() + " errorBody = " + parseError(response));
            return false;
        }
    }

    static private String parseError(Response<ResponseBody> response) {
        try {
            Error error = null;
            if (response.errorBody() != null) {
                error = RetrofitSingleTone
                        .getInstance()
                        .getGson()
                        .fromJson(response.errorBody().string(), Error.class);
            }
            if (error != null) {
                return error.getErrors();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static class Error {
        Message messages;

        public String getErrors() {
            return messages.getErrors();
        }
    }

    static class Message {
        List<ErrorItem> debug;
        List<ErrorItem> info;
        List<ErrorItem> success;
        List<ErrorItem> warning;
        List<ErrorItem> error;

        public String getErrors() {
            StringBuilder out = new StringBuilder();
            for (ErrorItem item : error) {
                out.append(item.body);
            }
            return out.toString();
        }
    }

    static class ErrorItem {
        String body;
        String title;
        int code;
    }

}