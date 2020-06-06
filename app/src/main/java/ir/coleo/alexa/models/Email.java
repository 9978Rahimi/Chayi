package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Email extends Chayi {

    @Expose
    protected String address;
    @Expose
    protected DateTime date_verified;

    public Email() {
    }

    public Email(Email email) {
        this.id = email.id;
        this.address = email.address;
        this.date_verified = email.date_verified;
    }

    public static final boolean start_verification_token = false;
    public static final boolean check_verification_token = false;
    public static final boolean create_token = true;
    public static final boolean create_on_item = false;
    public static final boolean start_verification_on_item = true;
    public static final boolean check_verification_on_item = true;


    public static RequestBody create_request(String address) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("email", object);
        object.add("address", new JsonPrimitive(address));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody start_verification_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody check_verification_request(String code) {
        JsonObject object = new JsonObject();
        object.add("code", new JsonPrimitive(code));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DateTime getDateVerified() {
        return date_verified;
    }

    public void setDateVerified(DateTime date_verified) {
        this.date_verified = date_verified;
    }


    public static String getPluralName() {
        return "emails";
    }

    public static String getSingleName() {
        return "email";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email temp = (Email) o;
        return this.id == temp.id;
    }
}