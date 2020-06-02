package ir.coleo.chayi.pipline.layers;

import ir.coleo.chayi.pipline.NetworkData;

public class TokenLayer extends NetworkLayer {

    public TokenLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        return data;
    }
}
