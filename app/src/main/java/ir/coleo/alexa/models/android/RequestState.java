package ir.coleo.alexa.models.android;

import com.google.gson.annotations.Expose;

public class RequestState {

    @Expose(serialize = false)
    public Integer state;
    @Expose(serialize = false)
    public Integer id;
    @Expose(serialize = false)
    public String text;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
