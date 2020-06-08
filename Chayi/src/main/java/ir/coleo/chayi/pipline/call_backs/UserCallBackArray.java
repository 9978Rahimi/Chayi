package ir.coleo.chayi.pipline.call_backs;

import java.util.ArrayList;

public interface UserCallBackArray<T> extends UserCallBack<T> {

    void success(ArrayList<T> t);

}
