package ir.coleo.chayi.pipline.layers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.call_backs.FailReason;

public class ConnectionLayer extends NetworkLayer {

    private NetworkLayer tempNextLayer;
    private String TAG = getClass().getSimpleName();

    public ConnectionLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Constants.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public NetworkData before(NetworkData data) {
        data.addResult(TAG, isNetworkAvailable());
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        if (!data.getResult(TAG)) {
            tempNextLayer = super.nextLayer;
            super.nextLayer = null;
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (!data.getResult(TAG) && data.isHandled()) {
            data.setHandled(true);
            super.nextLayer = tempNextLayer;
            data.getCallBack().fail(FailReason.Network);
        }
        return data;
    }
}
