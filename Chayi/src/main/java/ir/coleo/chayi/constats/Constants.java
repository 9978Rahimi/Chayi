package ir.coleo.chayi.constats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static androidx.security.crypto.MasterKey.KeyScheme.AES256_GCM;

/**
 * برای استفاده در اولین قسمت برنامه خود
 * متغییر های ‌
 * restartActivity
 * context
 * base_url
 * را مقدار دهی کنید
 */
public class Constants {

    public static String VERSION_NAME = "0.11.2";
    public static int VERSION_CODE = 40;

    public static String NO_TOKEN = "where is token";
    public static String TAG = "Constants chayi";
    public static String base_url = null;
    @SuppressLint("StaticFieldLeak")
    public static Context context = null;
    public static String TOKEN_STORAGE = "someWhereInDarkness";
    public static String TOKEN_DATA = "someWhereInDarkness12";
    public static String TOKEN_STATE = "isTokenSavedEncrypted";
    public static String TOKEN_STATE_VALUE = "isEncryptedToken";
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
        SharedPreferences sharedPreferences = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferencesState = context.getSharedPreferences(TOKEN_STATE, Context.MODE_PRIVATE);
            boolean encrypted_token = sharedPreferencesState.getBoolean(TOKEN_STATE_VALUE, true);
            if (encrypted_token) {
                try {
                    MasterKey masterKeyAlias = new MasterKey.Builder(context).setKeyScheme(AES256_GCM).build();
                    sharedPreferences = EncryptedSharedPreferences.create(context,
                            TOKEN_STORAGE,
                            masterKeyAlias,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                    Log.i(TAG, "setToken: EncryptedSharedPreferences ");
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
            }
        } else {
            sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        }
        return "Token " + sharedPreferences.getString(TOKEN_DATA, Constants.NO_TOKEN);
    }

    /**
     * ذخیره کلید ارطباط با سرور در حافظه
     */
    public static void setToken(String token) {
        SharedPreferences sharedPreferences = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                MasterKey masterKeyAlias = new MasterKey.Builder(context).setKeyScheme(AES256_GCM).build();
                sharedPreferences = EncryptedSharedPreferences.create(context,
                        TOKEN_STORAGE,
                        masterKeyAlias,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                Log.i(TAG, "setToken: EncryptedSharedPreferences ");
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
            }
        } else {
            sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_DATA, token);
        editor.commit();
    }

}
