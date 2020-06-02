package ir.coleo.chayi.pipline;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * use this class to pass data between layers
 */
public class NetworkData {

    private Type returnType;
    private String url;
    private ArrayList<Object> requestData;
    private UserCallBack<?> callBack;
    private RequestType requestType;

    public NetworkData(UserCallBack<?> callBack, RequestType requestType) {
        this.callBack = callBack;
        this.requestType = requestType;
        this.returnType = callBack.getClass().getGenericInterfaces()[0];
    }

    public NetworkData(UserCallBack<?> callBack) {
        this(callBack, RequestType.GET);
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
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
