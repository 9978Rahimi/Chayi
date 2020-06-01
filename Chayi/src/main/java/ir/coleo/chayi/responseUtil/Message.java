package ir.coleo.chayi.responseUtil;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Message {

    @Expose
    List<ErrorItem> debug;
    @Expose
    List<ErrorItem> info;
    @Expose
    List<ErrorItem> success;
    @Expose
    List<ErrorItem> warning;
    @Expose
    List<ErrorItem> error;

    public String getErrors() {
        StringBuilder out = new StringBuilder();
        for (ErrorItem item : error) {
            out.append(item.body);
        }
        return out.toString();
    }

}
