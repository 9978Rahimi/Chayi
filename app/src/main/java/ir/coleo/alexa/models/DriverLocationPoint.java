package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DriverLocationPoint extends Chayi {

    @Expose(serialize = false)
    protected Driver driver;
    @Expose(serialize = false)
    protected LocationPoint location_point;
    @Expose
    protected double accuracy;
    @Expose
    protected DateTime date_sampled;
    @Expose(serialize = false)
    protected DateTime date_created;

    public DriverLocationPoint() {
    }

    public DriverLocationPoint(DriverLocationPoint driver_location_point) {
        this.id = driver_location_point.id;
        this.driver = driver_location_point.driver;
        this.location_point = driver_location_point.location_point;
        this.accuracy = driver_location_point.accuracy;
        this.date_sampled = driver_location_point.date_sampled;
        this.date_created = driver_location_point.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(Driver driver, LocationPoint location_point) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("driver_location_point", object);
        if (driver == null) object.add("driver", null);
        else {
            JsonObject driver_object = new JsonObject();
            driver_object.add("id", new JsonPrimitive(driver.getId()));
            object.add("driver", driver_object);
        }
        if (location_point == null) object.add("location_point", null);
        else {
            JsonObject location_point_object = new JsonObject();
            location_point_object.add("id", new JsonPrimitive(location_point.getId()));
            object.add("location_point", location_point_object);
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

    public LocationPoint getLocationPoint() {
        return location_point;
    }

    public void setLocationPoint(LocationPoint location_point) {
        this.location_point = location_point;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public DateTime getDateSampled() {
        return date_sampled;
    }

    public void setDateSampled(DateTime date_sampled) {
        this.date_sampled = date_sampled;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }


    public static String getPluralName() {
        return "driver_location_points";
    }

    public static String getSingleName() {
        return "driver_location_point";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverLocationPoint temp = (DriverLocationPoint) o;
        return this.id == temp.id;
    }
}