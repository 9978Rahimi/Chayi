package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserUserGroup extends Chayi {

    @Expose
    protected BaseUser base_user;
    @Expose
    protected UserGroup user_group;
    @Expose(serialize = false)
    protected DateTime date_created;
    @Expose
    protected boolean is_active;

    public UserUserGroup() {
    }

    public UserUserGroup(UserUserGroup user_user_group) {
        this.id = user_user_group.id;
        this.base_user = user_user_group.base_user;
        this.user_group = user_user_group.user_group;
        this.date_created = user_user_group.date_created;
        this.is_active = user_user_group.is_active;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(UserGroup user_group, BaseUser base_user) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("user_user_group", object);
        if (user_group == null) object.add("user_group", null);
        else {
            JsonObject user_group_object = new JsonObject();
            user_group_object.add("id", new JsonPrimitive(user_group.getId()));
            object.add("user_group", user_group_object);
        }
        if (base_user == null) object.add("base_user", null);
        else {
            JsonObject base_user_object = new JsonObject();
            base_user_object.add("id", new JsonPrimitive(base_user.getId()));
            object.add("base_user", base_user_object);
        }
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public BaseUser getBaseUser() {
        return base_user;
    }

    public void setBaseUser(BaseUser base_user) {
        this.base_user = base_user;
    }

    public UserGroup getUserGroup() {
        return user_group;
    }

    public void setUserGroup(UserGroup user_group) {
        this.user_group = user_group;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }

    public boolean getIsActive() {
        return is_active;
    }

    public void setIsActive(boolean is_active) {
        this.is_active = is_active;
    }


    public static String getPluralName() {
        return "user_user_groups";
    }

    public static String getSingleName() {
        return "user_user_group";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserUserGroup temp = (UserUserGroup) o;
        return this.id == temp.id;
    }
}