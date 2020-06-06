package ir.coleo.chayi.pipline;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;

public interface UserCallBack<T extends Chayi> {

    void success(T t);

    void success(ArrayList<T> arrayList);

    void fail();

    // todo specify the fail re

}
