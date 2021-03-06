package ir.coleo.chayi.constats;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleTone {

    private static RetrofitSingleTone object;
    private String baseUrl;
    private Retrofit retrofit;
    private Gson gson;
    private ChayiInterface chayiInterface;
    private Context context;

    private RetrofitSingleTone() {
        this.baseUrl = Constants.base_url;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        chayiInterface = retrofit.create(ChayiInterface.class);
    }

    //todo validate base url

    public static RetrofitSingleTone getInstance() {
        if (object == null) {
            object = new RetrofitSingleTone();
        }
        return object;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Gson getGson() {
        return gson;
    }

    public ChayiInterface getChayiInterface() {
        return chayiInterface;
    }
}
