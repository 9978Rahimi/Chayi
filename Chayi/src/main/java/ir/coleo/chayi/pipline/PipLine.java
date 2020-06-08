package ir.coleo.chayi.pipline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.pipline.call_backs.UserCallBack;
import ir.coleo.chayi.pipline.layers.ConnectionLayer;
import ir.coleo.chayi.pipline.layers.HandleErrorLayer;
import ir.coleo.chayi.pipline.layers.JsonLayer;
import ir.coleo.chayi.pipline.layers.LoadingLayer;
import ir.coleo.chayi.pipline.layers.NetworkLayer;
import ir.coleo.chayi.pipline.layers.RequestLayer;
import ir.coleo.chayi.pipline.layers.ResponseLayer;
import ir.coleo.chayi.pipline.layers.TestLayer;
import ir.coleo.chayi.pipline.layers.TokenLayer;
import ir.coleo.chayi.pipline.layers.UrlLayer;

public class PipLine {

    private static final String TAG = PipLine.class.getSimpleName();
    private static boolean debug = true;
    private static PipLine instance;
    private ArrayList<NetworkLayer> layers;
    private static Queue<Thread> debugThreads = new ArrayDeque<>();
    private static boolean runningDebug = false;

    @Nullable
    private static SimpleIdlingResource mIdlingResource;

    private NetworkLayer loading;
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
        loading = new LoadingLayer(connection);
        layers.add(connection);
        layers.add(loading);
        if (debug) {
            test = new TestLayer(connection, getIdlingResource());
            layers.add(test);
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

    public static void request(String functionName, Class<? extends Chayi> input, boolean single,
                               UserCallBack<?> callBack, Object... args) {
        Thread t = new Thread(() -> {
            if (debug) {
                if (lockMutual()) {
                    innerRequest(functionName, input, single, callBack, args);
                    unlock();
                }
                next();
            } else {
                innerRequest(functionName, input, single, callBack, args);
            }

        });
        addOrStart(t);
    }

    private static void innerRequest(String functionName, Class<? extends Chayi> input,
                                     boolean single, UserCallBack<?> callBack, Object... args) {
        PipLine pipLine = getInstance();
        NetworkData data = new NetworkData(callBack, RequestType.CUSTOM_POST, input, single);
        data.setFunctionName(functionName);
        data.setRequestData(new ArrayList<>(Arrays.asList(args)));
        if (debug){
            pipLine.test.lunch(data);
        }else {
            pipLine.connection.lunch(data);
        }
    }

    public static void request(RequestType type, Chayi input, boolean single,
                               UserCallBack<?> callBack, Object... args) {
        Thread t = new Thread(() -> {
            if (debug) {
                if (lockMutual()) {
                    innerRequest(type, input, single, callBack, args);
                    unlock();
                }
                next();
            } else {
                innerRequest(type, input, single, callBack, args);
            }

        });
        addOrStart(t);
    }

    private static void innerRequest(RequestType type, Chayi input, boolean single,
                                     UserCallBack<?> callBack, Object... args) {
        PipLine pipLine = getInstance();
        NetworkData data = new NetworkData(callBack, type, input.getClass(), single);
        data.setId(input.getId());
        data.setRequestData(new ArrayList<>(Arrays.asList(args)));
        if (debug){
            pipLine.test.lunch(data);
        }else {
            pipLine.connection.lunch(data);
        }
    }

    public static void request(RequestType type, Class<? extends Chayi> input, boolean single,
                               UserCallBack<?> callBack, Object... args) {
        Thread t = new Thread(() -> {
            if (debug) {
                if (lockMutual()) {
                    innerRequest(type, input, single, callBack, args);
                    unlock();
                }
                next();
            } else {
                innerRequest(type, input, single, callBack, args);
            }

        });
        addOrStart(t);
    }

    private static void innerRequest(RequestType type, Class<? extends Chayi> input, boolean single,
                                     UserCallBack<?> callBack, Object... args) {
        PipLine pipLine = getInstance();
        if (debug){
            pipLine.test.lunch(new NetworkData(callBack, type, input, single));
        }else {
            pipLine.connection.lunch(new NetworkData(callBack, type, input, single));
        }

    }

    private static void next() {
        if (!debugThreads.isEmpty()) {
            debugThreads.poll().start();
        }
    }

    public static boolean isDebug() {
        return debug;
    }

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
