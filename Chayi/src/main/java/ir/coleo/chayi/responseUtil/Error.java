package ir.coleo.chayi.responseUtil;

import com.google.gson.annotations.Expose;

public class Error {

    @Expose
    Message messages;

    public String getErrors() {
        return messages.getErrors();
    }

}
