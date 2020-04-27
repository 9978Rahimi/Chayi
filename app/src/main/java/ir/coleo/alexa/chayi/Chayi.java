package ir.coleo.alexa.chayi;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class Chayi {

    private static String TAG = "chayi";

    protected int id;

    protected int getId() {
        return this.id;
    }

    public static ArrayList<Chayi> getAllRequest(Class<?> input) {
        String url = getAllUrl(input);
        Retrofit retrofit = RetrofitSingleTone.getInstance().getRetrofit();

        ChayiInterface chayiInterface = retrofit.create(ChayiInterface.class);

        Call<ResponseBody> repos = chayiInterface.get(url);

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    for (Chayi temp: Chayi.AllResponseParser(input,response.body().string())){
                        Log.i(TAG, "onResponse: " + temp.toString());
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

        return null;
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

    private static ArrayList<Chayi> AllResponseParser(Class<?> input, String response) {
        try {
            JSONArray array = new JSONArray(response);
            ArrayList<Chayi> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                if (i == 0){
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