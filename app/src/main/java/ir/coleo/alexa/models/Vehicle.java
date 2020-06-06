package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Vehicle extends Chayi {

    @Expose
    protected Driver driver;
    @Expose
    protected boolean is_enable;
    @Expose
    protected String plate_a;
    @Expose
    protected String plate_b;
    @Expose
    protected String plate_c;
    @Expose
    protected String plate_d;

    public Vehicle() {
    }

    public Vehicle(Vehicle vehicle) {
        this.id = vehicle.id;
        this.driver = vehicle.driver;
        this.is_enable = vehicle.is_enable;
        this.plate_a = vehicle.plate_a;
        this.plate_b = vehicle.plate_b;
        this.plate_c = vehicle.plate_c;
        this.plate_d = vehicle.plate_d;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(Driver driver, String plate_a, String plate_b, String plate_c, String plate_d) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("vehicle", object);
        if (driver == null) object.add("driver", null);
        else {
            JsonObject driver_object = new JsonObject();
            driver_object.add("id", new JsonPrimitive(driver.getId()));
            object.add("driver", driver_object);
        }
        object.add("plate_a", new JsonPrimitive(plate_a));
        object.add("plate_b", new JsonPrimitive(plate_b));
        object.add("plate_c", new JsonPrimitive(plate_c));
        object.add("plate_d", new JsonPrimitive(plate_d));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public boolean getIsEnable() {
        return is_enable;
    }

    public void setIsEnable(boolean is_enable) {
        this.is_enable = is_enable;
    }

    public String getPlateA() {
        return plate_a;
    }

    public void setPlateA(String plate_a) {
        this.plate_a = plate_a;
    }

    public String getPlateB() {
        return plate_b;
    }

    public void setPlateB(String plate_b) {
        this.plate_b = plate_b;
    }

    public String getPlateC() {
        return plate_c;
    }

    public void setPlateC(String plate_c) {
        this.plate_c = plate_c;
    }

    public String getPlateD() {
        return plate_d;
    }

    public void setPlateD(String plate_d) {
        this.plate_d = plate_d;
    }


    public static String getPluralName() {
        return "vehicles";
    }

    public static String getSingleName() {
        return "vehicle";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle temp = (Vehicle) o;
        return this.id == temp.id;
    }
}