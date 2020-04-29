package ir.coleo.alexa.chayi.testing_models;

import ir.coleo.alexa.chayi.Chayi;
import ir.coleo.alexa.chayi.constats.RetrofitSingleTone;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Todo extends Chayi {

    public static String urlAll = "waste_categories";

    public int userId;
    public boolean completed;
    public String title;

    public Todo(int userId, boolean completed, String title) {
        super.id = 1;
        this.userId = userId;
        this.completed = completed;
        this.title = title;
    }

    @Override
    public String getUrl() {
        return urlAll + "/" + getId();
    }

    @Override
    public RequestBody getJsonObject() {
        return RequestBody.create(MediaType.parse("json"), RetrofitSingleTone.getInstance().getGson().toJson(this));
    }

    @Override
    public String toString() {
        return "Todo{" +
                "userId=" + userId +
                ", id=" + id +
                ", completed=" + completed +
                ", title='" + title + '\'' +
                '}';
    }
}
