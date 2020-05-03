package ir.coleo.alexa.chayi.constats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Constants {

    public static String NO_TOKEN = "where is token";
    private static String TOKEN_STORAGE = "someWhereInDarkness";
    private static String TOKEN_DATA = "someWhereInDarkness12";

    public static String base_url = null;
    @SuppressLint("StaticFieldLeak")
    public static Context context = null;


    public static void setBase_url(String baseUrl) {
        Constants.base_url = baseUrl;
    }

    /**
     * گرفتن کلید ارتباط با سرور
     */
    public static String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_DATA, Constants.NO_TOKEN);
    }

    /**
     * ذخیره کلید ارطباط با سرور در حافظه
     */
    public static void setToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_DATA, token);
        editor.apply();
    }

}
