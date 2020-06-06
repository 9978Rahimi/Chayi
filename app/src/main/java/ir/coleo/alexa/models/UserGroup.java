package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserGroup extends Chayi {

    @Expose
    protected String title;
    @Expose
    protected Integer token_valid_seconds;

    public UserGroup() {
    }

    public UserGroup(UserGroup user_group) {
        this.id = user_group.id;
        this.title = user_group.title;
        this.token_valid_seconds = user_group.token_valid_seconds;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(String title, Integer token_valid_seconds) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("user_group", object);
        object.add("title", new JsonPrimitive(title));
        object.add("token_valid_seconds", new JsonPrimitive(token_valid_seconds));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTokenValidSeconds() {
        return token_valid_seconds;
    }

    public void setTokenValidSeconds(Integer token_valid_seconds) {
        this.token_valid_seconds = token_valid_seconds;
    }


    public static String getPluralName() {
        return "user_groups";
    }

    public static String getSingleName() {
        return "user_group";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroup temp = (UserGroup) o;
        return this.id == temp.id;
    }
}