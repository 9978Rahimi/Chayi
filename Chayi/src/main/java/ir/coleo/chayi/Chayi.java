package ir.coleo.chayi;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.annotations.Expose;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.coleo.chayi.callBack.ChayiCallBack;
import ir.coleo.chayi.callBack.SingleChayiCallBack;
import ir.coleo.chayi.constats.ChayiInterface;
import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.constats.RetrofitSingleTone;
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
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getObjectName(Class<?> input) {
        Method allUrl;
        try {
            allUrl = input.getMethod("getSingleName");
            return (String) allUrl.invoke(null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getAllRequest(Class<?> input, ChayiCallBack chayiCallBack) {
        String url = getAllUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos;
        repos = chayiInterface.get(url, Constants.getToken());

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    if (handleResponse(response, responseObject)) {
                        ArrayList<Chayi> chayis = Chayi.AllResponseParser(input, responseObject);
                        for (Chayi temp : chayis) {
                            Log.i(TAG, "onResponse: " + temp.toString());
                        }
                        chayiCallBack.onResponse(chayis);
                    } else {
                        chayiCallBack.fail("");
                        //todo

                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private static ArrayList<Chayi> AllResponseParser(Class<?> input, JSONObject response) {
        try {
            JSONArray array = response.getJSONArray(getAllUrl(input));
            ArrayList<Chayi> list = new ArrayList<>();
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

    private static Chayi responseParserPutOrPost(Class<?> input, JSONObject response) {
        try {
            return (Chayi) RetrofitSingleTone.getInstance().getGson()
                    .fromJson(response.get(getObjectName(input)).toString(), input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Chayi responseParser(Class<?> input, String response) {
        return (Chayi) RetrofitSingleTone.getInstance().getGson().fromJson(response, input);
    }

    private static boolean handleResponse(Response<ResponseBody> response, JSONObject object) {
        if (response.code() >= 100 && response.code() < 400) {
            if (response.body() != null) {
                try {
                    Constants.setToken(object.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            Log.i(TAG, "handleResponse: more than 400");
            if (response.code() == 403 && firstErrorCode(response) == 12) {
                Constants.setToken(Constants.NO_TOKEN);
                Intent intent = new Intent(Constants.context, Constants.getRestartActivity());
                Constants.context.startActivity(intent);
                ((Activity) Constants.context).finish();
            }
            return false;
        }
    }

    static private int firstErrorCode(Response<ResponseBody> response) {
        try {
            Error error = null;
            if (response.errorBody() != null) {
                error = RetrofitSingleTone
                        .getInstance()
                        .getGson()
                        .fromJson(response.errorBody().string(), Error.class);
            }
            if (error != null) {
                return error.messages.error.get(0).code;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    static private String parseError(Response<ResponseBody> response) {
        try {
            Error error = null;
            if (response.errorBody() != null) {
                error = RetrofitSingleTone
                        .getInstance()
                        .getGson()
                        .fromJson(response.errorBody().string(), Error.class);
            }
            if (error != null) {
                return error.getErrors();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getUrl(Class<?> input) {
        return getAllUrl(input) + "/" + id;
    }

    public RequestBody getJsonObject(Class<?> input) {
        JSONObject object = new JSONObject();
        try {
            object.put(getObjectName(input), new JSONObject(RetrofitSingleTone.getInstance().getGson().toJson(this)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody getCreateJsonObject(Class<?> input, Object... args) {
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
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    private static RequestBody getCustomRequestBody(Class<?> input, String function, Object... args) {
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
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    @Deprecated
    private static Object parseCustomRequestBody(Class<?> input, String function, Response<ResponseBody> response) {
        Method method;
        try {
            method = input.getMethod(function + "_response", Response.class);
            return method.invoke(null, response);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("json"), "{}");
    }

    private static boolean needToken(Class<?> input, String function) {
        try {
            Field field;
            field = input.getField(function + "_token");
            return field.getBoolean(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isOnItem(Class<?> input, String function) {
        try {
            Field field;
            field = input.getField(function + "_on_item");
            return field.getBoolean(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void customPostRequest(SingleChayiCallBack callBack, String function, Object inputObject,
                                         Object... args) {
        Class<?> input = inputObject.getClass();
        boolean onItem = isOnItem(input, function);
        String url;
        if (onItem) {
            if (inputObject instanceof Chayi) {
                url = getAllUrl(input) + "/" + ((Chayi) inputObject).getId() + "/" + function;
            } else {
                url = getAllUrl(input) + "/" + function;
            }
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
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response.body().string());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if (handleResponse(response, responseObject)) {
                    callBack.onResponse((Chayi) responseParserPutOrPost(input, responseObject));
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

    public static void customPostRequest(SingleChayiCallBack callBack, String function, Class<?> input,
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
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response.body().string());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if (response.code() > 100 && response.code() < 400) {
                    callBack.onResponse((Chayi) responseParserPutOrPost(input, responseObject));
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

    public void putRequest(SingleChayiCallBack callBack, Class<?> input) {
        String url = getUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.put(url, getJsonObject(input), Constants.getToken());


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response.body().string());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if (handleResponse(response, responseObject)) {
                    callBack.onResponse(responseParserPutOrPost(input, responseObject));
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

    public static void postRequest(SingleChayiCallBack callBack, Class<?> input, Object... args) {
        String url = getAllUrl(input);
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.post(url, getCreateJsonObject(input, args), Constants.getToken());

        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response.body().string());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if (handleResponse(response, responseObject)) {
                    callBack.onResponse(responseParserPutOrPost(input, responseObject));
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

    public void getRequest(SingleChayiCallBack callBack, Class<?> input) {
        String url = getUrl(this.getClass());
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.get(url, Constants.getToken());


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    if (handleResponse(response, responseObject)) {
                        Chayi newObject = RetrofitSingleTone.getInstance().getGson()
                                .fromJson(responseObject.getJSONObject(Objects.requireNonNull(getObjectName(input))).toString(), Chayi.this.getClass());

                        callBack.onResponse(newObject);
                    } else {
                        callBack.fail("");//todo
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void deleteRequest(SingleChayiCallBack callBack, Class<?> input) {
        String url = getUrl(getClass());
        ChayiInterface chayiInterface = RetrofitSingleTone.getInstance().getChayiInterface();

        Call<ResponseBody> repos = chayiInterface.delete(url, Constants.getToken());


        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    if (handleResponse(response, responseObject)) {
                        callBack.onResponse(null);
                    } else {
                        callBack.fail("");//todo
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    static class Error {
        Message messages;

        public String getErrors() {
            return messages.getErrors();
        }
    }

    static class Message {
        List<ErrorItem> debug;
        List<ErrorItem> info;
        List<ErrorItem> success;
        List<ErrorItem> warning;
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
        String body;
        String title;
        int code;
    }

    public int getId() {
        return id;
    }
}