package ir.coleo.alexa.testing_models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CitizenAddress extends Chayi {

    @Expose
    protected String title;
    @Expose
    protected Citizen citizen;
    @Expose
    protected LocationPoint location_point;
    @Expose
    protected String entered_address;
    @Expose
    protected String generated_address;
    @Expose
    protected String description;
    @Expose
    protected boolean was_last_used;
    @Expose
    protected Image static_map_file;

    public CitizenAddress() {
    }

    public CitizenAddress(CitizenAddress citizen_address) {
        this.id = citizen_address.id;
        this.title = citizen_address.title;
        this.citizen = citizen_address.citizen;
        this.location_point = citizen_address.location_point;
        this.entered_address = citizen_address.entered_address;
        this.generated_address = citizen_address.generated_address;
        this.description = citizen_address.description;
        this.was_last_used = citizen_address.was_last_used;
        this.static_map_file = citizen_address.static_map_file;
    }

    public static final boolean create_token = true;
    public static final boolean my_addresses_token = true;
    public static final boolean update_static_map_file_token = true;
    public static final boolean create_on_item = false;
    public static final boolean my_addresses_on_item = false;
    public static final boolean update_static_map_file_on_item = true;


    public static RequestBody create_request(String title, Citizen citizen, String generated_address, String entered_address, LocationPoint location_point, String description) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("citizen_address", object);
        object.add("title", new JsonPrimitive(title));
        if (citizen == null) object.add("citizen", null);
        else {
            JsonObject citizen_object = new JsonObject();
            citizen_object.add("id", new JsonPrimitive(citizen.getId()));
            object.add("citizen", citizen_object);
        }
        object.add("generated_address", new JsonPrimitive(generated_address));
        object.add("entered_address", new JsonPrimitive(entered_address));
        if (location_point == null) object.add("location_point", null);
        else {
            JsonObject location_point_object = new JsonObject();
            location_point_object.add("id", new JsonPrimitive(location_point.getId()));
            object.add("location_point", location_point_object);
        }
        if (description != null) {
            object.add("description", new JsonPrimitive(description));
        }
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody my_addresses_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody update_static_map_file_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public LocationPoint getLocationPoint() {
        return location_point;
    }

    public void setLocationPoint(LocationPoint location_point) {
        this.location_point = location_point;
    }

    public String getEnteredAddress() {
        return entered_address;
    }

    public void setEnteredAddress(String entered_address) {
        this.entered_address = entered_address;
    }

    public String getGeneratedAddress() {
        return generated_address;
    }

    public void setGeneratedAddress(String generated_address) {
        this.generated_address = generated_address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getWasLastUsed() {
        return was_last_used;
    }

    public void setWasLastUsed(boolean was_last_used) {
        this.was_last_used = was_last_used;
    }

    public Image getStaticMapFile() {
        return static_map_file;
    }

    public void setStaticMapFile(Image static_map_file) {
        this.static_map_file = static_map_file;
    }


    public static String getPluralName() {
        return "citizen_addresses";
    }

    public static String getSingleName() {
        return "citizen_address";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitizenAddress temp = (CitizenAddress) o;
        return this.id == temp.id;
    }
}