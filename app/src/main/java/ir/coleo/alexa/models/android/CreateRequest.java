package ir.coleo.alexa.models.android;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

import ir.coleo.alexa.models.CitizenAddress;
import ir.coleo.alexa.models.CitizenRequest;
import ir.coleo.alexa.models.CitizenRequestWaste;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreateRequest extends CitizenRequest {

    public static final boolean app_create_token = true;
    public static final boolean app_create_on_item = false;

    public static String getPluralName() {
        return "citizen_requests";
    }

    public static String getSingleName() {
        return "citizen_request";
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
            JsonObject innerArray = new JsonObject();
            innerArray.add("citizen_amount", new JsonPrimitive(wastes.get(i).getCitizenAmount()));
            JsonObject waste = new JsonObject();
            waste.add("id", new JsonPrimitive(wastes.get(i).getWaste().getId()));
            innerArray.add("waste", waste);
            wastes_array.add(innerArray);
        }
        object.add("wastes", wastes_array);
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

}
