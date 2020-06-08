package ir.coleo.chayi.pipline.layers;

import java.util.ArrayDeque;
import java.util.Queue;

import ir.coleo.chayi.constats.ChayiInterface;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.call_backs.FailReason;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestLayer extends NetworkLayer {

    private NetworkLayer tempNextLayer;
    private boolean fail = false;
    private static String TAG = RequestLayer.class.getSimpleName();
    private static boolean canWork = true;
    private Queue<Lock> locks = new ArrayDeque<>();

    public RequestLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();
        Call<ResponseBody> repos = null;
        switch (data.getRequestType()) {
            case GET:
                repos = chayiInterface.get(data.getUrl(), data.getToken());
                break;
            case PUT:
                repos = chayiInterface.put(data.getUrl(), data.getBody(), data.getToken());
                break;
            case POST:
                repos = chayiInterface.post(data.getUrl(), data.getBody(), data.getToken());
                break;
            case DELETE:
                repos = chayiInterface.delete(data.getUrl(), data.getToken());
                break;
            case CUSTOM_POST:
                if (data.isNeedToken()) {
                    repos = chayiInterface.post(data.getUrl(), data.getBody(), data.getToken());
                } else {
                    repos = chayiInterface.post(data.getUrl(), data.getBody());
                }
                break;
        }
        data.setCall(repos);
        return data;
    }


    @Override
    public NetworkData work(NetworkData data) {

        Object lock = new Object();

        data.getCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                data.setBodyResponse(response);
                fail = false;
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tempNextLayer = nextLayer;
                nextLayer = null;
                fail = true;
                synchronized (lock) {
                    lock.notify();
                }
            }
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        if (fail && data.isHandled()) {
            data.setHandled(true);
            nextLayer = tempNextLayer;
            data.getCallBack().fail(FailReason.Request);
        }
        return data;
    }

    static class Lock {

        final Call<ResponseBody> call;
        final Object lock;

        public Lock(Call<ResponseBody> call) {
            this.call = call;
            lock = new Object();
        }
    }

}
