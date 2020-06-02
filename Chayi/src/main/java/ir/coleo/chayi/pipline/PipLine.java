package ir.coleo.chayi.pipline;

import java.util.ArrayList;

import ir.coleo.chayi.pipline.layers.NetworkLayer;
import ir.coleo.chayi.pipline.layers.TestLayer;
import ir.coleo.chayi.pipline.layers.TokenLayer;

public class PipLine {

    private static final String TAG = PipLine.class.getSimpleName();
    private static boolean debug = true;
    private static PipLine instance;
    private ArrayList<NetworkLayer> layers;

    /**
     * layer will added to list reverse
     * last layer in array is first layer that start it's work
     */
    private PipLine() {
        layers = new ArrayList<>();
        layers.add(new TokenLayer(null));
        layers.add(new TokenLayer(layers.get(layers.size() - 1)));
        if (debug) {
            layers.add(new TestLayer(layers.get(layers.size() - 1)));
        }
    }

    private static PipLine getInstance() {
        if (instance == null) {
            instance = new PipLine();
        }
        return instance;
    }


    public static void request(RequestType type, UserCallBack<?> callBack) {
        PipLine pipLine = getInstance();
        NetworkLayer layer = pipLine.layers.get(pipLine.layers.size() - 1);
        layer.lunch(new NetworkData(callBack));
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        PipLine.debug = debug;
    }
}
