package ir.coleo.chayi;

import com.google.gson.annotations.Expose;

public abstract class Chayi {

    private static String TAG = "chayi";

    @Expose(serialize = false)
    protected int id;

    public int getId() {
        return id;
    }
}