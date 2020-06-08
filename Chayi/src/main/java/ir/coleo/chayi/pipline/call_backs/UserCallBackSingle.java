package ir.coleo.chayi.pipline.call_backs;

public interface UserCallBackSingle<T> extends UserCallBack<T> {

    void success(T t);

}
