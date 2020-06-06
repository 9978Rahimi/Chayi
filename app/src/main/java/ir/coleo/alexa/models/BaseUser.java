package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BaseUser extends Chayi {

    @Expose
    protected boolean is_active;
    @Expose
    protected String language;
    @Expose(serialize = false)
    protected DateTime date_created;

    public BaseUser() {
    }

    public BaseUser(BaseUser base_user) {
        this.id = base_user.id;
        this.is_active = base_user.is_active;
        this.language = base_user.language;
        this.date_created = base_user.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request() {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("base_user", object);
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public boolean getIsActive() {
        return is_active;
    }

    public void setIsActive(boolean is_active) {
        this.is_active = is_active;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }


    public static String getPluralName() {
        return "base_users";
    }

    public static String getSingleName() {
        return "base_user";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser temp = (BaseUser) o;
        return this.id == temp.id;
    }
}