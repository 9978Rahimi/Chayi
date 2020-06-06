package ir.coleo.chayi.pipline;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.pipline.layers.ConnectionLayer;
import ir.coleo.chayi.pipline.layers.HandleErrorLayer;
import ir.coleo.chayi.pipline.layers.JsonLayer;
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
        layers.add(new ConnectionLayer(layers.get(layers.size() - 1)));
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

    public static void request(String functionName, Class<? extends Chayi> input, boolean single, UserCallBack<?> callBack) {
        PipLine pipLine = getInstance();
        NetworkLayer layer = pipLine.layers.get(pipLine.layers.size() - 1);
        NetworkData data = new NetworkData(callBack, RequestType.CUSTOM_POST, input, single);
        data.setFunctionName(functionName);
        layer.lunch(data);
    }

    public static void request(RequestType type, Chayi input, boolean single, UserCallBack<?> callBack) {
        PipLine pipLine = getInstance();
        NetworkLayer layer = pipLine.layers.get(pipLine.layers.size() - 1);
        NetworkData data = new NetworkData(callBack, type, input.getClass(), single);
        data.setId(input.getId());
        layer.lunch(data);
    }

    public static void request(RequestType type, Class<? extends Chayi> input, boolean single, UserCallBack<?> callBack) {
        PipLine pipLine = getInstance();
        NetworkLayer layer = pipLine.layers.get(pipLine.layers.size() - 1);
        layer.lunch(new NetworkData(callBack, type, input, single));
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        PipLine.debug = debug;
    }
}
