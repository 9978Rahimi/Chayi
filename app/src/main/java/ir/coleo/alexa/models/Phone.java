package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Phone extends Chayi {

    @Expose
    protected String number;
    @Expose
    protected DateTime date_verified;

    public Phone() {
    }

    public Phone(Phone phone) {
        this.id = phone.id;
        this.number = phone.number;
        this.date_verified = phone.date_verified;
    }

    public static final boolean start_verification_token = false;
    public static final boolean check_verification_token = false;
    public static final boolean create_token = true;
    public static final boolean create_on_item = false;
    public static final boolean start_verification_on_item = true;
    public static final boolean check_verification_on_item = true;


    public static RequestBody create_request(String number) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("phone", object);
        object.add("number", new JsonPrimitive(number));
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DateTime getDateVerified() {
        return date_verified;
    }

    public void setDateVerified(DateTime date_verified) {
        this.date_verified = date_verified;
    }


    public static String getPluralName() {
        return "phones";
    }

    public static String getSingleName() {
        return "phone";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone temp = (Phone) o;
        return this.id == temp.id;
    }
}