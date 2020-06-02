package ir.coleo.alexa.testing_models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DriverAppVersion extends Chayi {

    @Expose
    protected String version_name;
    @Expose
    protected Integer version_code;
    @Expose(serialize = false)
    protected DateTime date_created;
    @Expose
    protected boolean available_update;
    @Expose
    protected boolean force_update;

    public DriverAppVersion() {
    }

    public DriverAppVersion(DriverAppVersion driver_app_version) {
        this.id = driver_app_version.id;
        this.version_name = driver_app_version.version_name;
        this.version_code = driver_app_version.version_code;
        this.date_created = driver_app_version.date_created;
        this.available_update = driver_app_version.available_update;
        this.force_update = driver_app_version.force_update;
    }

    public static final boolean create_token = true;
    public static final boolean version_code_find_token = true;
    public static final boolean create_on_item = false;
    public static final boolean version_code_find_on_item = false;


    public static RequestBody create_request(String version_name, Integer version_code, boolean available_update, boolean force_update) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("driver_app_version", object);
        object.add("version_name", new JsonPrimitive(version_name));
        object.add("version_code", new JsonPrimitive(version_code));
        object.add("available_update", new JsonPrimitive(available_update));
        object.add("force_update", new JsonPrimitive(force_update));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody version_code_find_request(Integer version_code) {
        JsonObject object = new JsonObject();
        object.add("version_code", new JsonPrimitive(version_code));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public String getVersionName() {
        return version_name;
    }

    public void setVersionName(String version_name) {
        this.version_name = version_name;
    }

    public Integer getVersionCode() {
        return version_code;
    }

    public void setVersionCode(Integer version_code) {
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


    public static String getPluralName() {
        return "driver_app_versions";
    }

    public static String getSingleName() {
        return "driver_app_version";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverAppVersion temp = (DriverAppVersion) o;
        return this.id == temp.id;
    }
}