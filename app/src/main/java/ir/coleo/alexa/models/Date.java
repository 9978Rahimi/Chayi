package ir.coleo.alexa.models;

import com.google.gson.annotations.Expose;

public class Date {

    @Expose(serialize = false)
    public String day_name;
    @Expose(serialize = false)
    public String month_name;
    @Expose
    public Integer year;
    @Expose
    public Integer month;
    @Expose
    public Integer day;

}