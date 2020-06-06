package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VoteChoice extends Chayi {

    @Expose
    protected String title;
    @Expose
    protected String on_stars;

    public VoteChoice() {
    }

    public VoteChoice(VoteChoice vote_choice) {
        this.id = vote_choice.id;
        this.title = vote_choice.title;
        this.on_stars = vote_choice.on_stars;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(String title, String on_stars) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("vote_choice", object);
        object.add("title", new JsonPrimitive(title));
        object.add("on_stars", new JsonPrimitive(on_stars));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOnStars() {
        return on_stars;
    }

    public void setOnStars(String on_stars) {
        this.on_stars = on_stars;
    }


    public static String getPluralName() {
        return "vote_choices";
    }

    public static String getSingleName() {
        return "vote_choice";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteChoice temp = (VoteChoice) o;
        return this.id == temp.id;
    }
}