package ir.coleo.chayi.callBack;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;


public interface ChayiCallBackOne<T extends Chayi> extends CallBackOne {


    void onResponse(ArrayList<T> list);


}
