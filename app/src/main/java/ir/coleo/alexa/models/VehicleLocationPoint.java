package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VehicleLocationPoint extends Chayi {

    @Expose
    protected Vehicle vehicle;
    @Expose
    protected LocationPoint location_point;
    @Expose(serialize = false)
    protected DateTime date_created;

    public VehicleLocationPoint() {
    }

    public VehicleLocationPoint(VehicleLocationPoint vehicle_location_point) {
        this.id = vehicle_location_point.id;
        this.vehicle = vehicle_location_point.vehicle;
        this.location_point = vehicle_location_point.location_point;
        this.date_created = vehicle_location_point.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(Vehicle vehicle, LocationPoint location_point) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("vehicle_location_point", object);
        if (vehicle == null) object.add("vehicle", null);
        else {
            JsonObject vehicle_object = new JsonObject();
            vehicle_object.add("id", new JsonPrimitive(vehicle.getId()));
            object.add("vehicle", vehicle_object);
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocationPoint getLocationPoint() {
        return location_point;
    }

    public void setLocationPoint(LocationPoint location_point) {
        this.location_point = location_point;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }


    public static String getPluralName() {
        return "vehicle_location_points";
    }

    public static String getSingleName() {
        return "vehicle_location_point";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleLocationPoint temp = (VehicleLocationPoint) o;
        return this.id == temp.id;
    }
}