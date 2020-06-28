package ir.coleo.chayi.pipline;

import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.pipline.call_backs.UserCallBack;
import ir.coleo.chayi.pipline.call_backs.UserCallBackArray;
import ir.coleo.chayi.pipline.call_backs.UserCallBackSingle;
import ir.coleo.chayi.pipline.call_backs.UserCallBackSingleStatus;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * use this class to pass data between layers
 */
public class NetworkData {

    private static String TAG = NetworkData.class.getSimpleName();

    private boolean needToken;
    private String token;

    private Class<? extends Chayi> input;
    private String functionName;
    private boolean onItem;
    private boolean single;
    private String url;

    private RequestBody body;
    private Call<ResponseBody> call;

    private Response<ResponseBody> bodyResponse;
    private JSONObject response;

    private ArrayList<Object> requestData;
    private UserCallBack<?> callBack;
    private RequestType requestType;
    private int id;

    private HashMap<String, Boolean> layerResult = new HashMap<>();

    private boolean handled = false;
    private View view;

    public NetworkData(UserCallBack<?> callBack, RequestType requestType, Class<? extends Chayi> input) {
        this.callBack = callBack;
        if (callBack instanceof UserCallBackSingle || callBack instanceof UserCallBackSingleStatus) {
            single = true;
        } else if (callBack instanceof UserCallBackArray) {
            single = false;
        }
        this.requestType = requestType;
        this.input = input;
    }

    public NetworkData(UserCallBack<?> callBack, Class<? extends Chayi> input) {
        this(callBack, RequestType.GET, input);
    }

    /**
     * نتیجه‌ی کار هر لایه برای استفاده در بازگشت در داده همان ریکوست در hash_map ذخیره می شود.
     * @param key
     * @param value
     */
    public void addResult(String key, Boolean value) {
        layerResult.put(key, value);
    }

    public Boolean getResult(String key) {
        return layerResult.get(key);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Call<ResponseBody> getCall() {
        return call;
    }

    public void setCall(Call<ResponseBody> call) {
        this.call = call;
    }

    public boolean isHandled() {
        return !handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }

    public Response<ResponseBody> getBodyResponse() {
        return bodyResponse;
    }

    public void setBodyResponse(Response<ResponseBody> bodyResponse) {
        this.bodyResponse = bodyResponse;
    }

    public RequestBody getBody() {
        return body;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    public boolean isNeedToken() {
        return needToken;
    }

    public void setNeedToken(boolean needToken) {
        this.needToken = needToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public Class<? extends Chayi> getInput() {
        return input;
    }

    public void setInput(Class<? extends Chayi> input) {
        this.input = input;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public boolean isOnItem() {
        return onItem;
    }

    public void setOnItem(boolean onItem) {
        this.onItem = onItem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Object> getRequestData() {
        return requestData;
    }

    public void setRequestData(ArrayList<Object> requestData) {
        this.requestData = requestData;
    }

    public UserCallBack<?> getCallBack() {
        return callBack;
    }

    public void setCallBack(UserCallBack<?> callBack) {
        this.callBack = callBack;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
