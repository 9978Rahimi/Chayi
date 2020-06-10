package ir.coleo.chayi.pipline.layers;

import android.util.Log;

import androidx.annotation.NonNull;

import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.SimpleIdlingResource;

public class TestLayer extends NetworkLayer {


    private String TAG = getClass().getSimpleName();

    @NonNull
    private final SimpleIdlingResource mIdlingResource;

    public TestLayer(NetworkLayer nextLayer, @NonNull SimpleIdlingResource mIdlingResource) {
        super(nextLayer);
        this.mIdlingResource = mIdlingResource;
    }

    @Override
    public NetworkData before(NetworkData data) {
        Log.i(TAG, "before: ");
        mIdlingResource.setIdleState(false);
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        Log.i(TAG, "work: ");
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        Log.i(TAG, "after: ");
        mIdlingResource.setIdleState(true);
        return data;
    }


}
