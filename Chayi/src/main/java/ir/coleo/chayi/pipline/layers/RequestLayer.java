package ir.coleo.chayi.pipline.layers;

import java.io.IOException;

import ir.coleo.chayi.constats.ChayiInterface;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.call_backs.FailReason;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RequestLayer extends NetworkLayer {

    private Call<ResponseBody> repos;
    private NetworkLayer tempNextLayer;
    private boolean fail = false;

    public RequestLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    @Override
    public NetworkData before(NetworkData data) {
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();
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
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        try {
            Response<ResponseBody> body = repos.execute();
            data.setBodyResponse(body);
            fail = false;
        } catch (IOException e) {
            tempNextLayer = nextLayer;
            nextLayer = null;
            fail = true;
            e.printStackTrace();
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
}
