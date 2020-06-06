package ir.coleo.alexa.models;

import com.google.gson.annotations.Expose;

public class File {

    @Expose(serialize = false)
    String file;
    @Expose
    Integer id;

    public Integer getId() {
        return id;
    }

    public String getFile() {
        return file;
    }

}