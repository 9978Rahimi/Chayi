package ir.coleo.chayi.callBack;

import ir.coleo.chayi.Chayi;

public interface SingleStatusChayiCallBack<T extends Chayi> extends SingleChayiCallBack<T> {

    void onResponse(T t, int statusCode);


}
