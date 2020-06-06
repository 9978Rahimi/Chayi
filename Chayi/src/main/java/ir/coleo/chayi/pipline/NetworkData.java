package ir.coleo.chayi.pipline;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;

/**
 * use this class to pass data between layers
 */
public class NetworkData {

    private static String TAG = NetworkData.class.getSimpleName();
    private boolean onItem;
    private boolean single;
    private String functionName;
    private Class<? extends Chayi> input;
    private String url;
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
