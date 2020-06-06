package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CitizenRequestVoteChoice extends Chayi {

    @Expose
    protected CitizenRequestVote vote;
    @Expose
    protected VoteChoice choice;

    public CitizenRequestVoteChoice() {
    }

    public CitizenRequestVoteChoice(CitizenRequestVoteChoice citizen_request_vote_choice) {
        this.id = citizen_request_vote_choice.id;
        this.vote = citizen_request_vote_choice.vote;
        this.choice = citizen_request_vote_choice.choice;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(CitizenRequestVote vote, VoteChoice choice) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("citizen_request_vote_choice", object);
        if (vote == null) object.add("vote", null);
        else {
            JsonObject vote_object = new JsonObject();
            vote_object.add("id", new JsonPrimitive(vote.getId()));
            object.add("vote", vote_object);
        }
        if (choice == null) object.add("choice", null);
        else {
            JsonObject choice_object = new JsonObject();
            choice_object.add("id", new JsonPrimitive(choice.getId()));
            object.add("choice", choice_object);
        }
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public CitizenRequestVote getVote() {
        return vote;
    }

    public void setVote(CitizenRequestVote vote) {
        this.vote = vote;
    }

    public VoteChoice getChoice() {
        return choice;
    }

    public void setChoice(VoteChoice choice) {
        this.choice = choice;
    }


    public static String getPluralName() {
        return "citizen_request_vote_choices";
    }

    public static String getSingleName() {
        return "citizen_request_vote_choice";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitizenRequestVoteChoice temp = (CitizenRequestVoteChoice) o;
        return this.id == temp.id;
    }
}