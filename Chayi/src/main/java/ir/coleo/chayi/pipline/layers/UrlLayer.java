package ir.coleo.chayi.pipline.layers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.RequestType;

public class UrlLayer extends NetworkLayer {

    private String TAG = getClass().getSimpleName();

    public UrlLayer(NetworkLayer nextLayer) {
        super(nextLayer);
    }

    public static String getAllUrl(Class<?> input) {
        Method allUrl;
        try {
            allUrl = input.getMethod("getPluralName");
            return (String) allUrl.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "null";
    }

    private static <T extends Chayi> boolean isOnItem(Class<T> input, String function) {
        try {
            Field field;
            field = input.getField(function + "_on_item");
            return field.getBoolean(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getObjectName(Class<?> input) {
        Method allUrl;
        try {
            allUrl = input.getMethod("getSingleName");
            return (String) allUrl.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "null";
    }

    @Override
    public NetworkData before(NetworkData data) {
        if (data.getRequestType() == RequestType.CUSTOM_POST)
            data.setOnItem(isOnItem(data.getInput(), data.getFunctionName()));
        return data;
    }

    @Override
    public NetworkData work(NetworkData data) {
        String url = getAllUrl(data.getInput());
        switch (data.getRequestType()) {
            case GET:
                if (data.isSingle()) {
                    url += ("/" + data.getId());
                }
                break;
            case PUT:
            case DELETE:
                url += ("/" + data.getId());
                break;
            case CUSTOM_POST:
                if (data.isOnItem()) {
                    url += ("/" + data.getId() + "/" + data.getFunctionName());
                } else {
                    url += ("/" + data.getFunctionName());
                }
                break;
            case POST:
                break;
        }
        data.setUrl(url);
        return data;
    }

    @Override
    public NetworkData after(NetworkData data) {
        return data;
    }
}
