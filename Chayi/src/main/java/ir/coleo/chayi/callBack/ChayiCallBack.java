package ir.coleo.chayi.callBack;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;


public interface ChayiCallBack<T extends Chayi> extends CallBack {


    void onResponse(ArrayList<T> list);


}
