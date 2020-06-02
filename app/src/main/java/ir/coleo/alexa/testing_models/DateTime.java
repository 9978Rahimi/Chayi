package ir.coleo.alexa.testing_models;

import com.google.gson.annotations.Expose;

public class DateTime {


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
    @Expose
    public Integer hour;
    @Expose
    public Integer minute;
    @Expose
    public Integer second;
    @Expose
    public Integer microsecond;

    public DateTime() {

    }

    public DateTime(DateTime datetime) {
        this.day_name = datetime.day_name;
        this.month_name = datetime.month_name;
        this.year = datetime.year;
        this.month = datetime.month;
        this.day = datetime.day;
        this.hour = datetime.hour;
        this.minute = datetime.minute;
        this.second = datetime.second;
        this.microsecond = datetime.microsecond;
    }

}