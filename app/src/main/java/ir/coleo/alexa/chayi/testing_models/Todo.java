package ir.coleo.alexa.chayi.testing_models;

import ir.coleo.alexa.chayi.Chayi;

public class Todo extends Chayi {

    public static String urlAll = "todos";

    public int userId;
    public boolean completed;
    public String title;

    @Override
    public String getUrl() {
        return urlAll + "/" + getId();
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
