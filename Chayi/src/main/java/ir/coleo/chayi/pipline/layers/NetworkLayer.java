package ir.coleo.chayi.pipline.layers;

import ir.coleo.chayi.pipline.Caller;
import ir.coleo.chayi.pipline.NetworkData;

public abstract class NetworkLayer implements Caller {

    protected NetworkLayer nextLayer;

    public NetworkLayer(NetworkLayer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public NetworkData lunch(NetworkData data) {
        data = before(data);
        data = work(data);
        if (nextLayer != null)
            data = nextLayer.lunch(data);
        return after(data);
    }

}
