package ir.coleo.chayi.callBack;

import ir.coleo.chayi.Chayi;

public interface SingleStatusChayiCallBackOne<T extends Chayi> extends SingleChayiCallBackOne<T> {

    void onResponse(T t, int statusCode);


}
