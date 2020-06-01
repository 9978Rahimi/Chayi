package ir.coleo.chayi.callBack;


import ir.coleo.chayi.Chayi;

public interface SingleChayiCallBack<T extends Chayi> extends CallBack {

    void onResponse(T T);


}
