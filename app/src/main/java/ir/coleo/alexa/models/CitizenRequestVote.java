package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CitizenRequestVote extends Chayi {

    @Expose
    protected CitizenRequest request;
    @Expose
    protected Integer star;
    @Expose
    protected String description;
    @Expose(serialize = false)
    protected DateTime date_created;

    public CitizenRequestVote() {
    }

    public CitizenRequestVote(CitizenRequestVote citizen_request_vote) {
        this.id = citizen_request_vote.id;
        this.request = citizen_request_vote.request;
        this.star = citizen_request_vote.star;
        this.description = citizen_request_vote.description;
        this.date_created = citizen_request_vote.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean app_create_token = true;
    public static final boolean create_on_item = false;
    public static final boolean app_create_on_item = false;


    public static RequestBody create_request(CitizenRequest request, Integer star, String description) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("citizen_request_vote", object);
        if (request == null) object.add("request", null);
        else {
            JsonObject request_object = new JsonObject();
            request_object.add("id", new JsonPrimitive(request.getId()));
            object.add("request", request_object);
        }
        object.add("star", new JsonPrimitive(star));
        object.add("description", new JsonPrimitive(description));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

//    public static RequestBody app_create_request(CitizenRequest request, Integer star, String description, ArrayList<VoteChoice> choices) {
//        JsonObject object = new JsonObject();
//        if (request == null) object.add("request", null);
//        else {
//            JsonObject request_object = new JsonObject();
//            request_object.add("id", new JsonPrimitive(request.getId()));
//            object.add("request", request_object);
//        }
//        object.add("star", new JsonPrimitive(star));
//        object.add("description", new JsonPrimitive(description));
//        JsonArray choices_array = new JsonArray();
//        for (Integer i = 0; i < choices.size(); i++) {
//            choices_array.add(RetrofitSingleTone.getInstance().getGson().toJsonTree(choices.get(i)));
//        }
//        object.add("choices", choices_array);
//        return RequestBody.create(MediaType.parse("json"), object.toString());
//    }

    public CitizenRequest getRequest() {
        return request;
    }

    public void setRequest(CitizenRequest request) {
        this.request = request;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }


    public static String getPluralName() {
        return "citizen_request_votes";
    }

    public static String getSingleName() {
        return "citizen_request_vote";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitizenRequestVote temp = (CitizenRequestVote) o;
        return this.id == temp.id;
    }
}