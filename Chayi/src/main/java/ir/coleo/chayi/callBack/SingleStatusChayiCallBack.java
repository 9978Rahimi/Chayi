package ir.coleo.chayi.callBack;


import ir.coleo.chayi.Chayi;

public interface SingleStatusChayiCallBack extends SingleChayiCallBack {

    void onResponse(Chayi chayi, int statusCode);


}
