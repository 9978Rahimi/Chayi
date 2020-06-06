package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Citizen extends Chayi {

    @Expose
    protected User user;
    @Expose
    protected FCMDevice firebase_device;
    @Expose(serialize = false)
    protected DateTime date_joined;
    @Expose
    protected CitizenAppVersion app_version;
    @Expose
    protected String introduce_code;
    @Expose
    protected Citizen introduced_by;
    @Expose
    protected DateTime date_terms_accepted;

    public Citizen() {
    }

    public Citizen(Citizen citizen) {
        this.id = citizen.id;
        this.user = citizen.user;
        this.firebase_device = citizen.firebase_device;
        this.date_joined = citizen.date_joined;
        this.app_version = citizen.app_version;
        this.introduce_code = citizen.introduce_code;
        this.introduced_by = citizen.introduced_by;
        this.date_terms_accepted = citizen.date_terms_accepted;
    }

    public static final boolean app_enter_token = false;
    public static final boolean app_check_code_token = false;
    public static final boolean create_token = true;
    public static final boolean get_own_object_token = true;
    public static final boolean set_app_version_token = true;
    public static final boolean update_firebase_token_token = true;
    public static final boolean edit_my_profile_token = true;
    public static final boolean create_on_item = false;
    public static final boolean get_own_object_on_item = false;
    public static final boolean set_app_version_on_item = false;
    public static final boolean update_firebase_token_on_item = false;
    public static final boolean edit_my_profile_on_item = false;
    public static final boolean app_enter_on_item = false;
    public static final boolean app_check_code_on_item = false;


    public static RequestBody create_request(User user, String firebase_token) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("citizen", object);
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

    public static RequestBody app_check_code_request(String phone, String code, String introduce_code) {
        JsonObject object = new JsonObject();
        object.add("phone", new JsonPrimitive(phone));
        object.add("code", new JsonPrimitive(code));
        if (introduce_code != null) {
            object.add("introduce_code", new JsonPrimitive(introduce_code));
        }
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

    public static RequestBody edit_my_profile_request(String first_name, String last_name, String national_number) {
        JsonObject object = new JsonObject();
        object.add("first_name", new JsonPrimitive(first_name));
        object.add("last_name", new JsonPrimitive(last_name));
        object.add("national_number", new JsonPrimitive(national_number));
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

    public CitizenAppVersion getAppVersion() {
        return app_version;
    }

    public void setAppVersion(CitizenAppVersion app_version) {
        this.app_version = app_version;
    }

    public String getIntroduceCode() {
        return introduce_code;
    }

    public void setIntroduceCode(String introduce_code) {
        this.introduce_code = introduce_code;
    }

    public Citizen getIntroducedBy() {
        return introduced_by;
    }

    public void setIntroducedBy(Citizen introduced_by) {
        this.introduced_by = introduced_by;
    }

    public DateTime getDateTermsAccepted() {
        return date_terms_accepted;
    }

    public void setDateTermsAccepted(DateTime date_terms_accepted) {
        this.date_terms_accepted = date_terms_accepted;
    }


    public static String getPluralName() {
        return "citizens";
    }

    public static String getSingleName() {
        return "citizen";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citizen temp = (Citizen) o;
        return this.id == temp.id;
    }
}