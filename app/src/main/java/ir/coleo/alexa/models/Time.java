package ir.coleo.alexa.models;

import com.google.gson.annotations.Expose;

public class Time {

    @Expose
    public Integer hour;
    @Expose
    public Integer minute;
    @Expose
    public Integer second;
    @Expose
    public Integer microsecond;

}