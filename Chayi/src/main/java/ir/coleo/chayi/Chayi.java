package ir.coleo.chayi;

import android.util.Log;

public abstract class Chayi {

    public abstract String getUrl();

    private String TAG = "chayi";
    private String base_url = "www.base.com/";

    public Chayi getRequest() {
        String url = base_url + this.getUrl();
        Log.i(TAG, "getRequest: url = " + url);


        return null;
    }


}
