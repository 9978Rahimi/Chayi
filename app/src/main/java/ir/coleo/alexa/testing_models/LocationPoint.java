package ir.coleo.alexa.testing_models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LocationPoint extends Chayi {

    @Expose
    protected double latitude;
    @Expose
    protected double longitude;

    public LocationPoint() {
    }

    public LocationPoint(LocationPoint location_point) {
        this.id = location_point.id;
        this.latitude = location_point.latitude;
        this.longitude = location_point.longitude;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(double latitude, double longitude) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("location_point", object);
        object.add("latitude", new JsonPrimitive(latitude));
        object.add("longitude", new JsonPrimitive(longitude));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public static String getPluralName() {
        return "location_points";
    }

    public static String getSingleName() {
        return "location_point";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationPoint temp = (LocationPoint) o;
        return this.id == temp.id;
    }
}