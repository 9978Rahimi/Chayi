package ir.coleo.chayi.pipline;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;
import okhttp3.RequestBody;

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

    private ArrayList<Object> requestData;
    private UserCallBack<?> callBack;
    private RequestType requestType;
    private int id;

    public NetworkData(UserCallBack<?> callBack, RequestType requestType, Class<? extends Chayi> input, boolean single) {
        this.callBack = callBack;
        this.requestType = requestType;
        this.single = single;
        this.input = input;
    }

    public NetworkData(UserCallBack<?> callBack, Class<? extends Chayi> input, boolean single) {
        this(callBack, RequestType.GET, input, single);
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
