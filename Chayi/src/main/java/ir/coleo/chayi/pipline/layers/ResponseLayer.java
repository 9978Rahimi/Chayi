package ir.coleo.chayi.pipline.layers;

import ir.coleo.chayi.pipline.NetworkData;

public class ResponseLayer extends NetworkLayer {

    public ResponseLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        return null;
    }

    @Override
    public NetworkData work(NetworkData data) {
        return null;
    }

    @Override
    public NetworkData after(NetworkData data) {
        return null;
    }
}
