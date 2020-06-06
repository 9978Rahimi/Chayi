package ir.coleo.alexa.models.android;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

import ir.coleo.alexa.models.CitizenRequest;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CitizenRequestVote extends ir.coleo.alexa.models.CitizenRequestVote {

    public static RequestBody app_create_request(CitizenRequest request, Integer star, ArrayList<VoteChoice> choices, String description) {
        JsonObject object = new JsonObject();
        if (request == null) object.add("request", null);
        else {
            JsonObject request_object = new JsonObject();
            request_object.add("id", new JsonPrimitive(request.getId()));
            object.add("request", request_object);
        }
        object.add("star", new JsonPrimitive(star));
        object.add("description", new JsonPrimitive(description));
        JsonArray choices_array = new JsonArray();
        for (Integer i = 0; i < choices.size(); i++) {
            JsonObject temp = new JsonObject();
            temp.add("id", new JsonPrimitive(choices.get(i).getId()));
            choices_array.add(temp);
        }
        object.add("choices", choices_array);
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }
}
