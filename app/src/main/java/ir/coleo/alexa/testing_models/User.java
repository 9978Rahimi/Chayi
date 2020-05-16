package ir.coleo.alexa.testing_models;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    public int id = 12;
    @Expose
    String name = "Alireza";

    public static String getSingleName() {
        return "user";
    }

    public static String getPluralName() {
        return "users";
    }

    public int getId() {
        return id;
    }
}
