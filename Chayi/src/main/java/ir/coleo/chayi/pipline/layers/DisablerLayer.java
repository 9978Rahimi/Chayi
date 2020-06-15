package ir.coleo.chayi.pipline.layers;

import android.app.Activity;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.NetworkData;

public class DisablerLayer extends NetworkLayer {

    private String TAG = getClass().getSimpleName();

    public DisablerLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        if (data.getView() != null) {
            ((Activity) Constants.context).runOnUiThread(() -> data.getView().setClickable(false));
            data.addResult(TAG, true);
        } else {
            data.addResult(TAG, false);
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (data.getResult(TAG)) {
            ((Activity) Constants.context).runOnUiThread(() -> data.getView().setClickable(true));
        }
        return data;
    }
}
