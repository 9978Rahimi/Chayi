package ir.coleo.chayi.responseUtil;

import com.google.gson.annotations.Expose;

public class ErrorItem {
    @Expose
    String body;
    @Expose
    String title;
    @Expose
    int code;

}
