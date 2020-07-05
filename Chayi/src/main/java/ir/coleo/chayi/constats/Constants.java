package ir.coleo.chayi.constats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * برای استفاده در اولین قسمت برنامه خود
 * متغییر های ‌
 * restartActivity
 * context
 * base_url
 * را مقدار دهی کنید
 */
public class Constants {

    public static String VERSION_NAME = "0.11.0";
    public static int VERSION_CODE = 38;

    public static String NO_TOKEN = "where is token";
    public static String TAG = "Constants chayi";
    public static String base_url = null;
    @SuppressLint("StaticFieldLeak")
    public static Context context = null;
    public static String TOKEN_STORAGE = "someWhereInDarkness";
    public static String TOKEN_DATA = "someWhereInDarkness12";
    private static Class<?> restartActivity;

    public static Class<?> getRestartActivity() {
        return restartActivity;
    }

    public static void setRestartActivity(Class<?> restartActivity) {
        Constants.restartActivity = restartActivity;
    }

    public static void setBase_url(String baseUrl) {
        Constants.base_url = baseUrl;
    }

    /**
     * گرفتن کلید ارتباط با سرور
     */
    public static String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        return "Token " + sharedPreferences.getString(TOKEN_DATA, Constants.NO_TOKEN);
    }

    /**
     * ذخیره کلید ارطباط با سرور در حافظه
     */
    public static void setToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_DATA, token);
        editor.commit();
    }

}
