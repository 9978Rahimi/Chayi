package ir.coleo.chayi.pipline;

public interface UserCallBack<T> {

    void success(T t);

    void fail();


}
