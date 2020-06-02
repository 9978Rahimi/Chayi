package ir.coleo.chayi.callBack;


import ir.coleo.chayi.Chayi;

public interface SingleChayiCallBackOne<T extends Chayi> extends CallBackOne {

    void onResponse(T T);


}
