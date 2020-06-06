package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DriverQueueItem extends Chayi {

    @Expose
    protected Driver driver;
    @Expose
    protected CitizenRequest request;
    @Expose
    protected DriverQueueItem parent;
    @Expose
    protected DateTime date_reach;
    @Expose(serialize = false)
    protected DateTime date_created;

    public DriverQueueItem() {
    }

    public DriverQueueItem(DriverQueueItem driver_queue_item) {
        this.id = driver_queue_item.id;
        this.driver = driver_queue_item.driver;
        this.request = driver_queue_item.request;
        this.parent = driver_queue_item.parent;
        this.date_reach = driver_queue_item.date_reach;
        this.date_created = driver_queue_item.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(Driver driver, CitizenRequest request, DriverQueueItem parent) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("driver_queue_item", object);
        if (driver == null) object.add("driver", null);
        else {
            JsonObject driver_object = new JsonObject();
            driver_object.add("id", new JsonPrimitive(driver.getId()));
            object.add("driver", driver_object);
        }
        if (request == null) object.add("request", null);
        else {
            JsonObject request_object = new JsonObject();
            request_object.add("id", new JsonPrimitive(request.getId()));
            object.add("request", request_object);
        }
        if (parent == null) object.add("parent", null);
        else {
            JsonObject parent_object = new JsonObject();
            parent_object.add("id", new JsonPrimitive(parent.getId()));
            object.add("parent", parent_object);
        }
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public CitizenRequest getRequest() {
        return request;
    }

    public void setRequest(CitizenRequest request) {
        this.request = request;
    }

    public DriverQueueItem getParent() {
        return parent;
    }

    public void setParent(DriverQueueItem parent) {
        this.parent = parent;
    }

    public DateTime getDateReach() {
        return date_reach;
    }

    public void setDateReach(DateTime date_reach) {
        this.date_reach = date_reach;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }


    public static String getPluralName() {
        return "driver_queue_items";
    }

    public static String getSingleName() {
        return "driver_queue_item";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverQueueItem temp = (DriverQueueItem) o;
        return this.id == temp.id;
    }
}