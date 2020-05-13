package ir.coleo.alexa.testing_models;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    public static RequestBody create_request(User user, String firebase_token) {
        JsonObject object = new JsonObject();
        object.add("firebase_token", new JsonPrimitive(firebase_token));

        if (user == null) {
            object.add("user", null);
        } else {
            JsonObject userObject = new JsonObject();
            userObject.add("id", new JsonPrimitive(user.getId()));
            object.add("user", userObject);
        }
        Log.i("TAG", "create_request: " + object.toString());
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }


    public static void app_enter_response(Response<ResponseBody> response) {
        //
        Log.i("CITIZEN", "app_enter_parser: ");
    }

    public static Citizen app_check_code_response(Response<ResponseBody> response) {
        try {
            JSONObject responseObject = new JSONObject(response.body().string());
            int tempInt = responseObject.getInt("fucking_int");
            String tempS = responseObject.getString("fucking_int");
            boolean tempbol = responseObject.getBoolean("fucking_int");
            //can return int
            //public static int

            //
            JSONArray array = responseObject.getJSONArray("array_name");
            ArrayList<Citizen> citizens = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JsonObject object = (JsonObject) array.get(i);
                citizens.add(RetrofitSingleTone.getInstance().getGson().fromJson(object, Citizen.class));
            }

            return RetrofitSingleTone.getInstance().getGson().fromJson(responseObject.get("citizen").toString(), Citizen.class);
            //can return citizens
            //public static ArrayList<Citizen> ...
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
