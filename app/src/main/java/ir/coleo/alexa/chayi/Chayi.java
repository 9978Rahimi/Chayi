package ir.coleo.alexa.chayi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import ir.coleo.alexa.chayi.callBack.ChayiCallBack;
import ir.coleo.alexa.chayi.callBack.SingleChayiCallBack;
import ir.coleo.alexa.chayi.constats.ChayiInterface;
import ir.coleo.alexa.chayi.constats.RetrofitSingleTone;
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

    public void getRequest(SingleChayiCallBack callBack) {
        String url = getUrl();
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.get(url);
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    Chayi newObject = RetrofitSingleTone.getInstance().getGson()
                            .fromJson(response.body().string(), Chayi.this.getClass());

                    callBack.onResponse(newObject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void getAllRequest(Class<?> input, ChayiCallBack chayiCallBack) {
        String url = getAllUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.get(url);

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    ArrayList<Chayi> chayis = Chayi.AllResponseParser(input, response.body().string());
                    for (Chayi temp : chayis) {
                        Log.i(TAG, "onResponse: " + temp.toString());
                    }
                    chayiCallBack.onResponse(chayis);
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

}