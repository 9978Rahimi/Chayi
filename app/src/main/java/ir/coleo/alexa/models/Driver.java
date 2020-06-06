package ir.coleo.alexa.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Driver extends Chayi {

    @Expose
    protected User user;
    @Expose
    protected FCMDevice firebase_device;
    @Expose(serialize = false)
    protected DateTime date_joined;
    @Expose
    protected DriverAppVersion app_version;
    @Expose
    protected boolean is_enable;
    @Expose
    protected String plate_a;
    @Expose
    protected String plate_b;
    @Expose
    protected String plate_c;
    @Expose
    protected String plate_d;

    public Driver() {
    }

    public Driver(Driver driver) {
        this.id = driver.id;
        this.user = driver.user;
        this.firebase_device = driver.firebase_device;
        this.date_joined = driver.date_joined;
        this.app_version = driver.app_version;
        this.is_enable = driver.is_enable;
        this.plate_a = driver.plate_a;
        this.plate_b = driver.plate_b;
        this.plate_c = driver.plate_c;
        this.plate_d = driver.plate_d;
    }

    public static final boolean app_enter_token = false;
    public static final boolean app_check_code_token = false;
    public static final boolean create_token = true;
    public static final boolean get_own_object_token = true;
    public static final boolean set_app_version_token = true;
    public static final boolean update_firebase_token_token = true;
    public static final boolean send_my_location_token = true;
    public static final boolean send_my_locations_token = true;
    public static final boolean disable_me_token = true;
    public static final boolean create_on_item = false;
    public static final boolean get_own_object_on_item = false;
    public static final boolean set_app_version_on_item = false;
    public static final boolean update_firebase_token_on_item = false;
    public static final boolean send_my_location_on_item = false;
    public static final boolean send_my_locations_on_item = false;
    public static final boolean disable_me_on_item = false;
    public static final boolean app_enter_on_item = false;
    public static final boolean app_check_code_on_item = false;


    public static RequestBody create_request(User user, String firebase_token) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("driver", object);
        if (user == null) object.add("user", null);
        else {
            JsonObject user_object = new JsonObject();
            user_object.add("id", new JsonPrimitive(user.getId()));
            object.add("user", user_object);
        }
        object.add("firebase_token", new JsonPrimitive(firebase_token));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody app_enter_request(String phone) {
        JsonObject object = new JsonObject();
        object.add("phone", new JsonPrimitive(phone));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody app_check_code_request(String phone, String code) {
        JsonObject object = new JsonObject();
        object.add("phone", new JsonPrimitive(phone));
        object.add("code", new JsonPrimitive(code));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody get_own_object_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody set_app_version_request(Integer app_version_id) {
        JsonObject object = new JsonObject();
        object.add("app_version_id", new JsonPrimitive(app_version_id));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody update_firebase_token_request(String firebase_token) {
        JsonObject object = new JsonObject();
        object.add("firebase_token", new JsonPrimitive(firebase_token));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody send_my_location_request(double latitude, double longitude, double accuracy, DateTime date_sampled) {
        JsonObject object = new JsonObject();
        object.add("latitude", new JsonPrimitive(latitude));
        object.add("longitude", new JsonPrimitive(longitude));
        object.add("accuracy", new JsonPrimitive(accuracy));
        if (date_sampled == null) object.add("date_sampled", null);
        else {
            JsonObject date_sampled_object = new JsonObject();
            date_sampled_object.add("year", new JsonPrimitive(date_sampled.year));
            date_sampled_object.add("month", new JsonPrimitive(date_sampled.month));
            date_sampled_object.add("day", new JsonPrimitive(date_sampled.day));
            date_sampled_object.add("hour", new JsonPrimitive(date_sampled.hour));
            date_sampled_object.add("minute", new JsonPrimitive(date_sampled.minute));
            date_sampled_object.add("second", new JsonPrimitive(date_sampled.second));
            date_sampled_object.add("microsecond", new JsonPrimitive(date_sampled.microsecond));
            object.add("date_sampled", date_sampled_object);
        }
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody send_my_locations_request(ArrayList<DriverLocationPoint> items) {
        JsonObject object = new JsonObject();
        JsonArray items_array = new JsonArray();
        for (Integer i = 0; i < items.size(); i++) {
            items_array.add(RetrofitSingleTone.getInstance().getGson().toJsonTree(items.get(i)));
        }
        object.add("items", items_array);
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody disable_me_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FCMDevice getFirebaseDevice() {
        return firebase_device;
    }

    public void setFirebaseDevice(FCMDevice firebase_device) {
        this.firebase_device = firebase_device;
    }

    public DateTime getDateJoined() {
        return date_joined;
    }

    public void setDateJoined(DateTime date_joined) {
        this.date_joined = date_joined;
    }

    public DriverAppVersion getAppVersion() {
        return app_version;
    }

    public void setAppVersion(DriverAppVersion app_version) {
        this.app_version = app_version;
    }

    public boolean getIsEnable() {
        return is_enable;
    }

    public void setIsEnable(boolean is_enable) {
        this.is_enable = is_enable;
    }

    public String getPlateA() {
        return plate_a;
    }

    public void setPlateA(String plate_a) {
        this.plate_a = plate_a;
    }

    public String getPlateB() {
        return plate_b;
    }

    public void setPlateB(String plate_b) {
        this.plate_b = plate_b;
    }

    public String getPlateC() {
        return plate_c;
    }

    public void setPlateC(String plate_c) {
        this.plate_c = plate_c;
    }

    public String getPlateD() {
        return plate_d;
    }

    public void setPlateD(String plate_d) {
        this.plate_d = plate_d;
    }


    public static String getPluralName() {
        return "drivers";
    }

    public static String getSingleName() {
        return "driver";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver temp = (Driver) o;
        return this.id == temp.id;
    }
}