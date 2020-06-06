package ir.coleo.chayi.pipline.layers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ir.coleo.chayi.pipline.NetworkData;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class JsonLayer extends NetworkLayer {

    public JsonLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    private static <T> RequestBody getCustomRequestBody(Class<T> input, String function, ArrayList<Object> objects) {
        Method[] methods;
        Method target = null;
        try {
            methods = input.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(function + "_request")) {
                    target = method;
                    break;
                }
            }
            if (target == null) {
                return RequestBody.create(MediaType.parse("json"), "{}");
            }
            Object output = target.invoke(null, objects.toArray());
            if (output instanceof RequestBody)
                return (RequestBody) output;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    public static <T> RequestBody getCreateJsonObject(Class<T> input, ArrayList<Object> objects) {
        Method[] methods;
        Method target = null;
        try {
            methods = input.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("create_request")) {
                    target = method;
                    break;
                }
            }
            if (target == null) {
                return RequestBody.create(MediaType.parse("json"), "{}");
            }
            Object output = target.invoke(null, objects.toArray());
            if (output instanceof RequestBody)
                return (RequestBody) output;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    @Override
    public NetworkData before(NetworkData data) {
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        switch (data.getRequestType()) {
            case DELETE:
            case GET:
                data.setBody(null);
                break;
            case PUT:
                break;
            case POST:
                data.setBody(getCreateJsonObject(data.getInput(), data.getRequestData()));
                break;
            case CUSTOM_POST:
                data.setBody(getCustomRequestBody(data.getInput(), data.getFunctionName(), data.getRequestData()));
                break;
        }
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        return data;
    }
}
