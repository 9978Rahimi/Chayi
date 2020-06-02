package ir.coleo.alexa.testing_models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HistoryRequest extends CitizenRequest {

    public HistoryRequest(CitizenRequest citizenRequest) {
        super(citizenRequest);
        super.price = citizenRequest.price;
        super.wastes = citizenRequest.wastes;
        super.state = citizenRequest.state;
        super.text = citizenRequest.text;
    }

    public HistoryRequest(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return super.price + " تومان";
    }

    public String getStateString() {
        return text;
    }

    public static RequestBody driver_weighting_request(ArrayList<CitizenRequestWaste> wastes) {
        JsonObject object = new JsonObject();
        JsonArray wastes_array = new JsonArray();
        for (int i = 0; i < wastes.size(); i++) {
            JsonObject temp = new JsonObject();
            CitizenRequestWaste waste = wastes.get(i);
            temp.add("driver_amount", new JsonPrimitive(waste.getDriverAmount()));
            JsonObject wasteJsonObject = new JsonObject();
            wasteJsonObject.add("id", new JsonPrimitive(waste.getWaste().getId()));
            temp.add("waste", wasteJsonObject);
            wastes_array.add(temp);
        }
        object.add("wastes", wastes_array);
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

}
