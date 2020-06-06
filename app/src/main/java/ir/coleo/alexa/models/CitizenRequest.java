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

public class CitizenRequest extends Chayi {

    @Expose
    protected CitizenAddress citizen_address;
    @Expose
    protected Driver driver;
    @Expose
    protected Transaction transaction;
    @Expose(serialize = false)
    protected DateTime date_created;
    @Expose
    protected DateTime date_queued;
    @Expose
    protected DateTime date_driver_assigned;
    @Expose
    protected DateTime date_driver_will_reach;
    @Expose
    protected DateTime date_driver_reached;
    @Expose
    protected DateTime date_weighted;
    @Expose
    protected DateTime date_citizen_canceled;
    @Expose
    protected DateTime date_admin_canceled;
    @Expose(serialize = false)
    public Integer price;
    @Expose(serialize = false)
    public Integer state;
    @Expose(serialize = false)
    public String text;
    @Expose(serialize = false)
    public ArrayList<CitizenRequestWaste> wastes;

    public CitizenRequest() {
    }

    public CitizenRequest(CitizenRequest citizen_request) {
        this.id = citizen_request.id;
        this.citizen_address = citizen_request.citizen_address;
        this.driver = citizen_request.driver;
        this.transaction = citizen_request.transaction;
        this.date_created = citizen_request.date_created;
        this.date_queued = citizen_request.date_queued;
        this.date_driver_assigned = citizen_request.date_driver_assigned;
        this.date_driver_will_reach = citizen_request.date_driver_will_reach;
        this.date_driver_reached = citizen_request.date_driver_reached;
        this.date_weighted = citizen_request.date_weighted;
        this.date_citizen_canceled = citizen_request.date_citizen_canceled;
        this.date_admin_canceled = citizen_request.date_admin_canceled;
    }

    public static final boolean create_token = true;
    public static final boolean driver_weighting_token = true;
    public static final boolean app_create_token = true;
    public static final boolean my_objects_token = true;
    public static final boolean cancel_token = true;
    public static final boolean driver_reached_token = true;
    public static final boolean app_get_data_message_token = true;
    public static final boolean push_data_message_token = true;
    public static final boolean create_on_item = false;
    public static final boolean driver_weighting_on_item = true;
    public static final boolean app_create_on_item = false;
    public static final boolean my_objects_on_item = false;
    public static final boolean cancel_on_item = true;
    public static final boolean driver_reached_on_item = true;
    public static final boolean app_get_data_message_on_item = false;
    public static final boolean push_data_message_on_item = false;


    public static RequestBody create_request(CitizenAddress citizen_address) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("citizen_request", object);
        if (citizen_address == null) object.add("citizen_address", null);
        else {
            JsonObject citizen_address_object = new JsonObject();
            citizen_address_object.add("id", new JsonPrimitive(citizen_address.getId()));
            object.add("citizen_address", citizen_address_object);
        }
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody driver_weighting_request(ArrayList<CitizenRequestWaste> wastes) {
        JsonObject object = new JsonObject();
        JsonArray wastes_array = new JsonArray();
        for (Integer i = 0; i < wastes.size(); i++) {
            wastes_array.add(RetrofitSingleTone.getInstance().getGson().toJsonTree(wastes.get(i)));
        }
        object.add("wastes", wastes_array);
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody app_create_request(CitizenAddress citizen_address, ArrayList<CitizenRequestWaste> wastes) {
        JsonObject object = new JsonObject();
        if (citizen_address == null) object.add("citizen_address", null);
        else {
            JsonObject citizen_address_object = new JsonObject();
            citizen_address_object.add("id", new JsonPrimitive(citizen_address.getId()));
            object.add("citizen_address", citizen_address_object);
        }
        JsonArray wastes_array = new JsonArray();
        for (Integer i = 0; i < wastes.size(); i++) {
            wastes_array.add(RetrofitSingleTone.getInstance().getGson().toJsonTree(wastes.get(i)));
        }
        object.add("wastes", wastes_array);
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody my_objects_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody cancel_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody driver_reached_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody app_get_data_message_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody push_data_message_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public CitizenAddress getCitizenAddress() {
        return citizen_address;
    }

    public void setCitizenAddress(CitizenAddress citizen_address) {
        this.citizen_address = citizen_address;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }

    public DateTime getDateQueued() {
        return date_queued;
    }

    public void setDateQueued(DateTime date_queued) {
        this.date_queued = date_queued;
    }

    public DateTime getDateDriverAssigned() {
        return date_driver_assigned;
    }

    public void setDateDriverAssigned(DateTime date_driver_assigned) {
        this.date_driver_assigned = date_driver_assigned;
    }

    public DateTime getDateDriverWillReach() {
        return date_driver_will_reach;
    }

    public void setDateDriverWillReach(DateTime date_driver_will_reach) {
        this.date_driver_will_reach = date_driver_will_reach;
    }

    public DateTime getDateDriverReached() {
        return date_driver_reached;
    }

    public void setDateDriverReached(DateTime date_driver_reached) {
        this.date_driver_reached = date_driver_reached;
    }

    public DateTime getDateWeighted() {
        return date_weighted;
    }

    public void setDateWeighted(DateTime date_weighted) {
        this.date_weighted = date_weighted;
    }

    public DateTime getDateCitizenCanceled() {
        return date_citizen_canceled;
    }

    public void setDateCitizenCanceled(DateTime date_citizen_canceled) {
        this.date_citizen_canceled = date_citizen_canceled;
    }

    public DateTime getDateAdminCanceled() {
        return date_admin_canceled;
    }

    public void setDateAdminCanceled(DateTime date_admin_canceled) {
        this.date_admin_canceled = date_admin_canceled;
    }


    public static String getPluralName() {
        return "citizen_requests";
    }

    public static String getSingleName() {
        return "citizen_request";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitizenRequest temp = (CitizenRequest) o;
        return this.id == temp.id;
    }
}