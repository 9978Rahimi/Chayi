package ir.coleo.alexa.chayi.constats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleTone {

    private String baseUrl;
    private static RetrofitSingleTone object;
    private Retrofit retrofit;
    private Gson gson;
    private ChayiInterface chayiInterface;

    private RetrofitSingleTone() {
        this.baseUrl = Constants.base_url;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
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
