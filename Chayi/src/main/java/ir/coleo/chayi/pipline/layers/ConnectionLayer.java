package ir.coleo.chayi.pipline.layers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.NetworkData;

public class ConnectionLayer extends NetworkLayer {

    private boolean isConnected = false;
    private NetworkLayer tempNextLayer;

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
        isConnected = isNetworkAvailable();
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        if (!isConnected) {
            tempNextLayer = super.nextLayer;
            super.nextLayer = null;
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (!isConnected) {
            super.nextLayer = tempNextLayer;
            data.getCallBack().fail();
        }
        return data;
    }
}
