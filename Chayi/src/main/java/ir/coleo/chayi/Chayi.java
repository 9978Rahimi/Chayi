package ir.coleo.chayi;

import com.google.gson.annotations.Expose;

import ir.coleo.chayi.constats.Constants;

public abstract class Chayi {

    private static String TAG = "chayi";

    @Expose(serialize = false)
    protected int id;

    public int getId() {
        return id;
    }


    public static boolean haveToken() {
        return !Constants.getToken().equals("Token " + Constants.NO_TOKEN);
    }
}