package ir.coleo.chayi.pipline.layers;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.SimpleIdlingResource;

/**
 * لایه‌ی تست با استفاده از SimpleIdlingResource , Espresso
 *
 */
public class TestLayer extends NetworkLayer {

    private String TAG = getClass().getSimpleName();

    @NonNull
    private final SimpleIdlingResource mIdlingResource;
    private AtomicInteger count = new AtomicInteger(0);

    public TestLayer(NetworkLayer nextLayer, @NonNull SimpleIdlingResource mIdlingResource) {
        super(nextLayer);
        this.mIdlingResource = mIdlingResource;
    }

    @Override
    public NetworkData before(NetworkData data) {
        if (count.getAndAdd(1) == 0)
            mIdlingResource.setIdleState(false);
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (count.getAndAdd(-1) == 1)
            mIdlingResource.setIdleState(true);
        return data;
    }


}
