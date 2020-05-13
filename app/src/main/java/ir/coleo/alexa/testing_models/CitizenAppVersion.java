package ir.coleo.alexa.testing_models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CitizenAppVersion extends Chayi {

    @Expose
    private String version_name;
    @Expose
    private int version_code;
    @Expose(serialize = false)
    private DateTime date_created;
    @Expose
    private boolean available_update;
    @Expose
    private boolean force_update;

    public CitizenAppVersion(String version_name, int version_code, boolean available_update, boolean force_update) {
        this.version_name = version_name;
        this.version_code = version_code;
        this.available_update = available_update;
        this.force_update = force_update;
    }

    public static String getPluralName() {
        return "citizen_app_versions";
    }

    public static String getSingleName() {
        return "citizen_app_version";
    }

    public static final boolean version_code_find_token = true;


    public static RequestBody version_code_find_request(int version_code) {
        JsonObject object = new JsonObject();
        object.add("version_code", new JsonPrimitive(version_code));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static CitizenAppVersion version_code_find_response(Response<ResponseBody> response) {
        try {
            JSONObject responseObject = new JSONObject(response.body().string());
            return RetrofitSingleTone.getInstance().getGson().fromJson(responseObject.get("citizen_app_version").toString(), CitizenAppVersion.class);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVersionName() {
        return version_name;
    }

    public void setVersionName(String version_name) {
        this.version_name = version_name;
    }

    public int getVersionCode() {
        return version_code;
    }

    public void setVersionCode(int version_code) {
        this.version_code = version_code;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }

    public boolean getAvailableUpdate() {
        return available_update;
    }

    public void setAvailableUpdate(boolean available_update) {
        this.available_update = available_update;
    }

    public boolean getForceUpdate() {
        return force_update;
    }

    public void setForceUpdate(boolean force_update) {
        this.force_update = force_update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitizenAppVersion temp = (CitizenAppVersion) o;
        return this.id == temp.id;
    }
}