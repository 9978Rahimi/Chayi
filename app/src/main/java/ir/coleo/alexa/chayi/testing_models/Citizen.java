package ir.coleo.alexa.chayi.testing_models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import ir.coleo.alexa.chayi.Chayi;
import ir.coleo.alexa.chayi.constats.RetrofitSingleTone;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Citizen extends Chayi {

    public static String urlAll = "citizens";

    String phone;

    public Citizen(String phone) {
        this.phone = phone;
    }

    @Override
    public String getUrl() {
        return urlAll + "/" + getId();
    }

    @Override
    public RequestBody getJsonObject() {
        return RequestBody.create(MediaType.parse("json"), RetrofitSingleTone.getInstance().getGson().toJson(this));
    }

    public RequestBody app_enter() {
        JsonObject object = new JsonObject();
        object.add("phone", new JsonPrimitive(phone));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

}
