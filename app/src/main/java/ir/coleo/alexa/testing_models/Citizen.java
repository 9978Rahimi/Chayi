package ir.coleo.alexa.testing_models;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class Citizen extends Chayi {

    public static final boolean app_enter_token = false;
    public static final boolean app_check_code_token = false;

    public static String getSingleName() {
        return "citizen";
    }

    public static String getPluralName() {
        return "citizens";
    }

    public static RequestBody app_enter_request(String phone) {
        JsonObject object = new JsonObject();
        object.add("phone", new JsonPrimitive(phone));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static void app_enter_response(Response<ResponseBody> response) {
        //
        Log.i("CITIZEN", "app_enter_parser: ");
    }

    public static Citizen app_check_code_response(Response<ResponseBody> response) {
        try {
            JSONObject responseObject = new JSONObject(response.body().string());
            return RetrofitSingleTone.getInstance().getGson().fromJson(responseObject.get("citizen").toString(), Citizen.class);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RequestBody app_check_code_request(String phone, String code, String introduce_code) {
        JsonObject citizen = new JsonObject();
        citizen.add("phone", new JsonPrimitive(phone));
        citizen.add("code", new JsonPrimitive(code));
        citizen.add("introduce_code", new JsonPrimitive(introduce_code));
        return RequestBody.create(MediaType.parse("json"), citizen.toString());
    }

}
