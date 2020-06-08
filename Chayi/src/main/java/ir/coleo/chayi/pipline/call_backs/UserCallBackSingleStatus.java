package ir.coleo.chayi.pipline.call_backs;

public interface UserCallBackSingleStatus<T> extends UserCallBack<T> {

    void success(T t, int status);

}
