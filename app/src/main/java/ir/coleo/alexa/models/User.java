package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class User extends Chayi {

    @Expose(serialize = false)
    protected BaseUser base_user;
    @Expose
    protected String first_name;
    @Expose
    protected String last_name;
    @Expose
    protected String national_number;

    public User() {
    }

    public User(User user) {
        this.id = user.id;
        this.base_user = user.base_user;
        this.first_name = user.first_name;
        this.last_name = user.last_name;
        this.national_number = user.national_number;
    }

    public static final boolean test_token = false;
    public static final boolean create_token = true;
    public static final boolean create_on_item = false;
    public static final boolean test_on_item = false;


    public static RequestBody create_request(BaseUser base_user, String first_name, String last_name, String national_number) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("user", object);
        if (base_user != null) {
            if (base_user == null) object.add("base_user", null);
            else {
                JsonObject base_user_object = new JsonObject();
                base_user_object.add("id", new JsonPrimitive(base_user.getId()));
                object.add("base_user", base_user_object);
            }
        }
        object.add("first_name", new JsonPrimitive(first_name));
        object.add("last_name", new JsonPrimitive(last_name));
        object.add("national_number", new JsonPrimitive(national_number));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody test_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public BaseUser getBaseUser() {
        return base_user;
    }

    public void setBaseUser(BaseUser base_user) {
        this.base_user = base_user;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getNationalNumber() {
        return national_number;
    }

    public void setNationalNumber(String national_number) {
        this.national_number = national_number;
    }


    public static String getPluralName() {
        return "users";
    }

    public static String getSingleName() {
        return "user";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User temp = (User) o;
        return this.id == temp.id;
    }
}