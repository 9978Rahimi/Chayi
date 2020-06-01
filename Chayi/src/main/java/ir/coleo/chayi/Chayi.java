package ir.coleo.chayi;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.coleo.chayi.callBack.ChayiCallBack;
import ir.coleo.chayi.callBack.SingleChayiCallBack;
import ir.coleo.chayi.callBack.SingleStatusChayiCallBack;
import ir.coleo.chayi.constats.ChayiInterface;
import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.constats.RetrofitSingleTone;
import ir.hatamiarash.toast.RTLToast;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public abstract class Chayi {

    private static String TAG = "chayi";

    @Expose(serialize = false)
    protected int id;

    public static boolean haveToken() {
        return !Constants.getToken().equals("Token " + Constants.NO_TOKEN);
    }

    private static String getAllUrl(Class<?> input) {
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

    private static String getObjectName(Class<?> input) {
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

    public static <T extends Chayi> void getAllRequest(Class<T> input, ChayiCallBack<T> chayiCallBack) {
        String url = getAllUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        repos = chayiInterface.get(url, Constants.getToken());

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ChayiResponse chayiResponse = handleResponse(response);
                if (chayiResponse.ok) {
                    ArrayList<T> chayis = Chayi.AllResponseParser(input, chayiResponse.response);
                    chayiCallBack.onResponse(chayis);
                } else {
                    chayiCallBack.fail("");
                }

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private static <T extends Chayi> ArrayList<T> AllResponseParser(Class<?> input, JSONObject response) {
        try {
            JSONArray array = response.getJSONArray(getAllUrl(input));
            ArrayList<T> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                if (i == 0) {
                    Log.i(TAG, "AllResponseParser: " + array.getJSONObject(i).toString());
                }
                list.add(responseParser(input, array.getJSONObject(i).toString()));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static <T extends Chayi> T responseParserPutOrPost(Class<?> input, JSONObject response) {
        try {
            return RetrofitSingleTone.getInstance().getGson()
                    .fromJson(response.get(getObjectName(input)).toString(), (Type) input);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T extends Chayi> T responseParser(Class<?> input, String response) {
        return RetrofitSingleTone.getInstance().getGson().fromJson(response, (Type) input);
    }

    private static ChayiResponse handleResponse(Response<ResponseBody> response) {
        if (response.code() >= 100 && response.code() < 400) {
            if (response.body() != null) {
                JSONObject responseObject;
                try {
                    responseObject = new JSONObject(response.body().string());
                    Constants.setToken(responseObject.getString("token"));
                    return new ChayiResponse(true, responseObject);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
            return new ChayiResponse(true, null);
        } else {
            Error error = null;
            if (response.errorBody() != null) {
                try {
                    error = RetrofitSingleTone
                            .getInstance()
                            .getGson()
                            .fromJson(response.errorBody().string(), Error.class);
                } catch (IOException | JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
            if (error != null) {
                if (response.code() == 403) {
                    if (haveToken()) {
                        RTLToast.error(Constants.context, error.getErrors(), Toast.LENGTH_LONG).show();
                        Constants.setToken(Constants.NO_TOKEN);
                        Intent intent = new Intent(Constants.context, Constants.getRestartActivity());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Constants.context.startActivity(intent);
                        ((Activity) Constants.context).finish();
                    }
                } else {
                    RTLToast.error(Constants.context, error.getErrors(), Toast.LENGTH_LONG).show();
                }
            }

            return new ChayiResponse(false, null);
        }
    }

    public String getUrl(Class<?> input) {
        return getAllUrl(input) + "/" + id;
    }

    public static <T extends Chayi> JSONObject getOnlyId(T input) {
        JSONObject output = new JSONObject();
        try {
            for (Field field : input.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Expose.class))
                    if (field.getType().isPrimitive()) {
                        output.put(field.getName(), field.get(input));
                    } else if (field.getType().isInstance(String.class)) {
                        output.put(field.getName(), field.get(input));
                    } else if (!field.getType().isInstance(List.class)) {
                        if (field.get(input) != null) {
                            try {
                                Object target = field.get(input);
                                if (target != null) {
                                    JSONObject innerObject = new JSONObject();
                                    int id = (int) target.getClass().getDeclaredField("id").get(target);
                                    innerObject.put("id", id);
                                    output.put(field.getName(), innerObject);
                                }
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Object value = field.get(input);
                        if (value instanceof List) {
                            List<?> tempList = (List<?>) value;
                            if (!tempList.isEmpty()) {
                                JSONArray array = new JSONArray();
                                for (Object temp : tempList) {
                                    if (temp instanceof Chayi) {
                                        JSONObject innerObject = new JSONObject();
                                        try {
                                            int id = (int) temp.getClass().getDeclaredField("id").get(temp);
                                            innerObject.put("id", id);
                                            array.put(innerObject);
                                        } catch (NoSuchFieldException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                output.put(field.getName(), array);
                            }
                        }
                    }
            }

        } catch (JSONException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return output;
    }

    private static <T extends Chayi> JSONArray convertToArrayOfId(List<T> list) throws IllegalAccessException, JSONException {
        JSONArray array = new JSONArray();
        for (T temp : list) {
            if (temp.getClass().isPrimitive() || temp.getClass().isInstance(""))
                continue;
            JSONObject innerObject = new JSONObject();
            try {
                int id = (int) temp.getClass().getDeclaredField("id").get(temp);
                innerObject.put("id", id);
                array.put(innerObject);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return array;
    }


    public JSONObject getPutJsonObject(Object input, boolean inner) {
        JSONObject object = new JSONObject();
        try {
            JSONObject output = new JSONObject();
            for (Field field : input.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Expose.class))
                    if (field.getType().isPrimitive()) {
                        output.put(field.getName(), field.get(input));
                    } else if (field.getType().isInstance("")) {
                        output.put(field.getName(), field.get(input));
                    } else if (!field.getType().isInstance(List.class)) {
                        Object temp = field.get(input);
                        if (temp != null)
                            output.put(field.getName(), getPutJsonObject(temp, true));
                    }
            }
            Class<?> superClass = input.getClass().getSuperclass();
            if (superClass != null)
                for (Field field : superClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Expose.class))
                        if (field.getType().isPrimitive()) {
                            output.put(field.getName(), field.get(input));
                        } else if (field.getType().isInstance("")) {
                            output.put(field.getName(), field.get(input));
                        } else if (!field.getType().isInstance(List.class)) {
                            Object temp = field.get(input);
                            if (temp != null)
                                output.put(field.getName(), getPutJsonObject(temp, true));
                        }
                }
            if (inner) {
                return output;
            } else {
                object.put(Objects.requireNonNull(getObjectName(input.getClass())), output);
            }
        } catch (JSONException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }


    public static <T> RequestBody getCreateJsonObject(Class<T> input, Object... args) {
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
            Object output = target.invoke(null, args);
            if (output instanceof RequestBody)
                return (RequestBody) output;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    private static <T> RequestBody getCustomRequestBody(Class<T> input, String function, Object... args) {
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
            Object output = target.invoke(null, args);
            if (output instanceof RequestBody)
                return (RequestBody) output;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    private static <T> boolean needToken(Class<T> input, String function) {
        try {
            Field field;
            field = input.getField(function + "_token");
            return field.getBoolean(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static <T> boolean isOnItem(Class<T> input, String function) {
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

    public static <T extends Chayi> void customPostRequest(SingleChayiCallBack<? extends Chayi> callBack, String function, T inputObject,
                                                           Object... args) {
        Class<?> input = inputObject.getClass();
        Class<?> superClass = input.getSuperclass();
        if (superClass != null && superClass.isInstance(Chayi.class)) {
            boolean onItem = isOnItem(input, function);
            String url;
            if (onItem) {
                url = getAllUrl(input) + "/" + (inputObject).getId() + "/" + function;
            } else {
                url = getAllUrl(input) + "/" + function;
            }

            boolean token = needToken(input, function);
            RequestBody body = getCustomRequestBody(input, function, args);

            ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

            Call<ResponseBody> repos;
            if (token)
                repos = chayiInterface.post(url, body, Constants.getToken());
            else
                repos = chayiInterface.post(url, body);

            repos.enqueue(new Callback<ResponseBody>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ChayiResponse chayiResponse = handleResponse(response);
                    if (chayiResponse.ok) {
                        if (callBack instanceof SingleStatusChayiCallBack) {
                            ((SingleStatusChayiCallBack<?>) callBack).onResponse(responseParserPutOrPost(input, chayiResponse.response), response.code());
                        } else if (callBack != null)
                            callBack.onResponse(responseParserPutOrPost(input, chayiResponse.response));
                    } else {
                        callBack.fail("");
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public static <T extends Chayi> void customPostRequest(ChayiCallBack<T> callBack, String function, T inputObject,
                                                           Object... args) {
        Class<?> input = inputObject.getClass();
        boolean onItem = isOnItem(input, function);
        String url;
        if (onItem) {
            url = getAllUrl(input) + "/" + (inputObject).getId() + "/" + function;
        } else {
            url = getAllUrl(input) + "/" + function;
        }

        boolean token = needToken(input, function);
        RequestBody body = getCustomRequestBody(input, function, args);

        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        if (token)
            repos = chayiInterface.post(url, body, Constants.getToken());
        else
            repos = chayiInterface.post(url, body);

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ChayiResponse chayiResponse = handleResponse(response);
                if (chayiResponse.ok) {
                    ArrayList<T> chayis = Chayi.AllResponseParser(input, chayiResponse.response);
                    callBack.onResponse(chayis);
                } else {
                    callBack.fail("");
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static <T extends Chayi> void customPostRequest(ChayiCallBack<T> callBack, String function, Class<?> input,
                                                           Object... args) {
        boolean onItem = isOnItem(input, function);
        String url = getAllUrl(input) + "/" + function;

        boolean token = needToken(input, function);
        RequestBody body = getCustomRequestBody(input, function, args);

        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        if (token)
            repos = chayiInterface.post(url, body, Constants.getToken());
        else
            repos = chayiInterface.post(url, body);

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ChayiResponse chayiResponse = handleResponse(response);
                if (chayiResponse.ok) {
                    ArrayList<T> chayis = Chayi.AllResponseParser(input, chayiResponse.response);
                    callBack.onResponse(chayis);
                } else {
                    callBack.fail("");
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static <T extends Chayi> void customPostRequest(SingleChayiCallBack<T> callBack, String function, Class<T> input,
                                                           Object... args) {
        boolean onItem = isOnItem(input, function);
        String url = getAllUrl(input) + "/" + function;

        boolean token = needToken(input, function);
        RequestBody body = getCustomRequestBody(input, function, args);

        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        if (token)
            repos = chayiInterface.post(url, body, Constants.getToken());
        else
            repos = chayiInterface.post(url, body);

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ChayiResponse chayiResponse = handleResponse(response);
                if (chayiResponse.ok) {
                    if (callBack instanceof SingleStatusChayiCallBack) {
                        ((SingleStatusChayiCallBack<T>) callBack).onResponse(responseParserPutOrPost(input, chayiResponse.response), response.code());
                    } else if (callBack != null)
                        callBack.onResponse(responseParserPutOrPost(input, chayiResponse.response));
                } else {
                    callBack.fail("");
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public <T extends Chayi> void putRequest(SingleChayiCallBack<T> callBack, Class<?> input) {
        String url = getUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        String jsonString = getPutJsonObject(Objects.requireNonNull(input.cast(this)), false).toString();
        Log.i(TAG, "putRequest: " + jsonString);
        RequestBody body = RequestBody.create(MediaType.parse("json"), jsonString);
        Call<ResponseBody> repos = chayiInterface.put(url, body, Constants.getToken());


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ChayiResponse chayiResponse = handleResponse(response);
                if (chayiResponse.ok) {
                    callBack.onResponse(responseParserPutOrPost(input, chayiResponse.response));
                } else {
                    callBack.fail("");
                }

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public static <T extends Chayi> void postRequest(SingleChayiCallBack<T> callBack, Class<?> input, Object... args) {
        String url = getAllUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.post(url, getCreateJsonObject(input, args), Constants.getToken());

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ChayiResponse chayiResponse = handleResponse(response);
                if (chayiResponse.ok) {
                    callBack.onResponse(responseParserPutOrPost(input, chayiResponse.response));
                } else {
                    callBack.fail("");
                }

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public <T extends Chayi> void getRequest(SingleChayiCallBack<T> callBack, Class<?> input) {
        String url = getUrl(this.getClass());
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.get(url, Constants.getToken());


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ChayiResponse chayiResponse = handleResponse(response);
                    if (chayiResponse.ok) {
                        T newObject = RetrofitSingleTone.getInstance().getGson()
                                .fromJson(chayiResponse.response.getJSONObject(Objects.requireNonNull(getObjectName(input))).toString(), (Type) Chayi.this.getClass());

                        callBack.onResponse(newObject);
                    } else {
                        callBack.fail("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public <T extends Chayi> void deleteRequest(SingleChayiCallBack<T> callBack, Class<?> input) {
        String url = getUrl(getClass());
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.delete(url, Constants.getToken());


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ChayiResponse chayiResponse = handleResponse(response);
                if (chayiResponse.ok) {
                    callBack.onResponse(null);
                } else {
                    callBack.fail("");
                }

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    static class Error {
        @Expose
        Message messages;

        public String getErrors() {
            return messages.getErrors();
        }
    }

    static class Message {
        @Expose
        List<ErrorItem> debug;
        @Expose
        List<ErrorItem> info;
        @Expose
        List<ErrorItem> success;
        @Expose
        List<ErrorItem> warning;
        @Expose
        List<ErrorItem> error;

        public String getErrors() {
            StringBuilder out = new StringBuilder();
            for (ErrorItem item : error) {
                out.append(item.body);
            }
            return out.toString();
        }
    }

    static class ErrorItem {
        @Expose
        String body;
        @Expose
        String title;
        @Expose
        int code;
    }

    static class ChayiResponse {
        boolean ok;
        JSONObject response;

        public ChayiResponse(boolean ok, JSONObject response) {
            this.ok = ok;
            this.response = response;
        }
    }

    public int getId() {
        return id;
    }
}