package ir.coleo.alexa.testing_models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CitizenRequestWaste extends Chayi {

    @Expose
    protected CitizenRequest request;
    @Expose
    protected Waste waste;
    @Expose
    protected double citizen_amount;
    @Expose
    protected double driver_amount;

    public CitizenRequestWaste() {
    }

    public CitizenRequestWaste(CitizenRequestWaste citizen_request_waste) {
        this.id = citizen_request_waste.id;
        this.request = citizen_request_waste.request;
        this.waste = citizen_request_waste.waste;
        this.citizen_amount = citizen_request_waste.citizen_amount;
        this.driver_amount = citizen_request_waste.driver_amount;
    }

    public static final boolean create_token = true;
    public static final boolean wastes_of_request_token = true;
    public static final boolean create_on_item = false;
    public static final boolean wastes_of_request_on_item = false;


    public static RequestBody create_request(CitizenRequest request, Waste waste, Integer citizen_amount, Integer driver_amount) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("citizen_request_waste", object);
        if (request == null) object.add("request", null);
        else {
            JsonObject request_object = new JsonObject();
            request_object.add("id", new JsonPrimitive(request.getId()));
            object.add("request", request_object);
        }
        if (waste == null) object.add("waste", null);
        else {
            JsonObject waste_object = new JsonObject();
            waste_object.add("id", new JsonPrimitive(waste.getId()));
            object.add("waste", waste_object);
        }
        if (citizen_amount != null) {
            object.add("citizen_amount", new JsonPrimitive(citizen_amount));
        }
        if (driver_amount != null) {
            object.add("driver_amount", new JsonPrimitive(driver_amount));
        }
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody wastes_of_request_request(Integer request_id) {
        JsonObject object = new JsonObject();
        object.add("request_id", new JsonPrimitive(request_id));
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public CitizenRequest getRequest() {
        return request;
    }

    public void setRequest(CitizenRequest request) {
        this.request = request;
    }

    public Waste getWaste() {
        return waste;
    }

    public void setWaste(Waste waste) {
        this.waste = waste;
    }

    public double getCitizenAmount() {
        return citizen_amount;
    }

    public void setCitizenAmount(double citizen_amount) {
        this.citizen_amount = citizen_amount;
    }

    public double getDriverAmount() {
        return driver_amount;
    }

    public void setDriverAmount(double driver_amount) {
        this.driver_amount = driver_amount;
    }


    public static String getPluralName() {
        return "citizen_request_wastes";
    }

    public static String getSingleName() {
        return "citizen_request_waste";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitizenRequestWaste temp = (CitizenRequestWaste) o;
        return this.id == temp.id;
    }
}