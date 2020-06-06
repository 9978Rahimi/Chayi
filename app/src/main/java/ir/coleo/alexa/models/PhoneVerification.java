package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PhoneVerification extends Chayi {

    @Expose
    protected Phone phone;
    @Expose
    protected String verification_code;
    @Expose(serialize = false)
    protected DateTime verification_date;
    @Expose
    protected String tried_codes;

    public PhoneVerification() {
    }

    public PhoneVerification(PhoneVerification phone_verification) {
        this.id = phone_verification.id;
        this.phone = phone_verification.phone;
        this.verification_code = phone_verification.verification_code;
        this.verification_date = phone_verification.verification_date;
        this.tried_codes = phone_verification.tried_codes;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(Phone phone, String verification_code) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("phone_verification", object);
        if (phone == null) object.add("phone", null);
        else {
            JsonObject phone_object = new JsonObject();
            phone_object.add("id", new JsonPrimitive(phone.getId()));
            object.add("phone", phone_object);
        }
        object.add("verification_code", new JsonPrimitive(verification_code));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verification_code;
    }

    public void setVerificationCode(String verification_code) {
        this.verification_code = verification_code;
    }

    public DateTime getVerificationDate() {
        return verification_date;
    }

    public void setVerificationDate(DateTime verification_date) {
        this.verification_date = verification_date;
    }

    public String getTriedCodes() {
        return tried_codes;
    }

    public void setTriedCodes(String tried_codes) {
        this.tried_codes = tried_codes;
    }


    public static String getPluralName() {
        return "phone_verifications";
    }

    public static String getSingleName() {
        return "phone_verification";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneVerification temp = (PhoneVerification) o;
        return this.id == temp.id;
    }
}