package ir.coleo.chayi.pipline;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.pipline.call_backs.FailReason;
import ir.coleo.chayi.pipline.call_backs.UserCallBack;
import ir.coleo.chayi.pipline.layers.ConnectionLayer;
import ir.coleo.chayi.pipline.layers.DisablerLayer;
import ir.coleo.chayi.pipline.layers.HandleErrorLayer;
import ir.coleo.chayi.pipline.layers.JsonLayer;
import ir.coleo.chayi.pipline.layers.NetworkLayer;
import ir.coleo.chayi.pipline.layers.RequestLayer;
import ir.coleo.chayi.pipline.layers.ResponseLayer;
import ir.coleo.chayi.pipline.layers.TestLayer;
import ir.coleo.chayi.pipline.layers.TokenLayer;
import ir.coleo.chayi.pipline.layers.UrlLayer;

/**
 * سیستم چند لایه برای ارتباط با سرور
 * ورودی و خروجی هر لایه شی از کلاس NetworkData است
 * هر لایه عملیات مربوط به خود را انجام می‌دهد و لایه‌ی بعدی را صدا می‌زند
 * کلاس به صورت single tone پیاده شده است
 * <p>
 * برای استفاده باید متد‌های static را call کنید
 * request یا requestDisable
 */
public class PipLine {

    private static final String TAG = PipLine.class.getSimpleName();
    private static boolean debug = true;
    private static PipLine instance;
    private ArrayList<NetworkLayer> layers;
    private static Queue<Thread> debugThreads = new ArrayDeque<>();
    private static boolean runningDebug = false;

    @Nullable
    private static SimpleIdlingResource mIdlingResource;

    /**
     * لایه هایی که می‌توان ارتباط را با آن‌ها شروع کرد
     */
    private NetworkLayer disabler;
    private NetworkLayer connection;
    private NetworkLayer test;

    /**
     * layer will added to list reverse
     * last layer in array is first layer that start it's work
     */
    private PipLine() {
        layers = new ArrayList<>();
        layers.add(new ResponseLayer(null));
        layers.add(new HandleErrorLayer(layers.get(layers.size() - 1)));
        layers.add(new RequestLayer(layers.get(layers.size() - 1)));
        layers.add(new JsonLayer(layers.get(layers.size() - 1)));
        layers.add(new UrlLayer(layers.get(layers.size() - 1)));
        layers.add(new TokenLayer(layers.get(layers.size() - 1)));
        connection = new ConnectionLayer(layers.get(layers.size() - 1));
        disabler = new DisablerLayer(connection);
        layers.add(connection);
        layers.add(disabler);
        if (debug) {
            test = new TestLayer(disabler, getIdlingResource());
            layers.add(test);
        }
        if (ChayiJsonParser.getInstance().getDataClasses() == null) {
            Log.e(TAG, "PipLine: JSON problem");
        }
    }


    private static PipLine getInstance() {
        if (instance == null) {
            instance = new PipLine();
        }
        return instance;
    }

    private static void addOrStart(Thread t) {
        if (debug) {
            if (runningDebug) {
                debugThreads.add(t);
            } else {
                t.start();
            }
        } else {
            t.start();
        }
    }

    /**
     * @param type     نوع ریکوست
     * @param input    نوع داده‌ی برگشتی
     * @param callBack واسط برگشت داده به برنامه
     * @param view     شی از برنامه که قصد غیر فعال کردن آن در هنگام ارتباط را دارید
     * @param args     پارامتر های ورودی که برای ساختن شی ارتباط لازم هستند
     *                 توجه کنید که در _ post_costume اولین پارامتر باید نام تابع موجود در input باشد
     *                 <p>
     *                 این تابع بر روی یک شی صدا زده می شود
     *                 citizen/14/function
     */
    public static void requestDisable(RequestType type, Chayi input,
                                      UserCallBack<?> callBack, View view, Object... args) {
        Thread t = new Thread(() -> {
            innerRequest(type, input, callBack, view, args);
        });
        t.start();
    }

    public static void request(RequestType type, Chayi input,
                               UserCallBack<?> callBack, Object... args) {
        Thread t = new Thread(() -> {
            innerRequest(type, input, callBack, null, args);
        });
        t.start();
    }

    private static void innerRequest(RequestType type, Chayi input,
                                     UserCallBack<?> callBack, View view, Object... args) {
        PipLine pipLine = getInstance();
        NetworkData data = new NetworkData(callBack, type, input.getClass());
        data.setId(input.getId());
        ArrayList<Object> dataUser = new ArrayList<>(Arrays.asList(args));
        boolean loading = view != null;
        if (loading) {
            data.setView(view);
        }
        if (type == RequestType.CUSTOM_POST) {
            if (args[0] != null && args[0] instanceof String) {
                data.setFunctionName((String) args[0]);
                dataUser.remove(0);
                data.setRequestData(dataUser);
            } else {
                data.setHandled(true);
                callBack.fail(FailReason.Function_Name_Costume_Request);
                return;
            }
        } else {
            data.setRequestData(dataUser);
        }
        if (debug) {
            pipLine.test.lunch(data);
        } else {
            if (loading) {
                pipLine.disabler.lunch(data);
            } else
                pipLine.connection.lunch(data);
        }
    }

    /**
     * @param type     نوع ریکوست
     * @param input    نوع داده‌ی برگشتی
     * @param callBack واسط برگشت داده به برنامه
     * @param view     شی از برنامه که قصد غیر فعال کردن آن در هنگام ارتباط را دارید
     * @param args     پارامتر های ورودی که برای ساختن شی ارتباط لازم هستند
     *                 توجه کنید که در _ post_costume اولین پارامتر باید نام تابع موجود در input باشد
     */
    public static void requestDisable(RequestType type, Class<? extends Chayi> input,
                                      UserCallBack<?> callBack, View view, Object... args) {
        Thread t = new Thread(() -> {
            innerRequest(type, input, callBack, view, args);
        });
        t.start();
    }


    public static void request(RequestType type, Class<? extends Chayi> input,
                               UserCallBack<?> callBack, Object... args) {
        Thread t = new Thread(() -> {
            innerRequest(type, input, callBack, null, args);
        });
        t.start();
    }

    private static void innerRequest(RequestType type, Class<? extends Chayi> input,
                                     UserCallBack<?> callBack, View view, Object... args) {
        PipLine pipLine = getInstance();
        NetworkData data = new NetworkData(callBack, type, input);
        ArrayList<Object> dataUser = new ArrayList<>(Arrays.asList(args));
        boolean loading = view != null;
        if (loading) {
            data.setView(view);
        }
        if (type == RequestType.CUSTOM_POST) {
            if (args[0] != null && args[0] instanceof String) {
                data.setFunctionName((String) args[0]);
                dataUser.remove(0);
                data.setRequestData(dataUser);
            } else {
                data.setHandled(true);
                callBack.fail(FailReason.Function_Name_Costume_Request);
                return;
            }
        } else {
            data.setRequestData(dataUser);
        }
        if (debug) {
            pipLine.test.lunch(data);
        } else {
            if (loading) {
                pipLine.disabler.lunch(data);
            } else
                pipLine.connection.lunch(data);
        }

    }

    private static void next() {
        if (!debugThreads.isEmpty()) {
            Objects.requireNonNull(debugThreads.poll()).start();
        }
    }


    /**
     * مشخص می کند که برنامه در حالت دیباگ است یا نه
     */
    public static boolean isDebug() {
        return debug;
    }

    /**
     * مشخص می کند که برنامه در حالت دیباگ است یا نه
     */
    public static void setDebug(boolean debug) {
        PipLine.debug = debug;
    }

    private static boolean lockMutual() {
        if (!runningDebug) {
            runningDebug = true;
            return true;
        }
        return false;
    }

    private static void unlock() {
        runningDebug = false;
    }

    @VisibleForTesting
    @NonNull
    public static SimpleIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


}
