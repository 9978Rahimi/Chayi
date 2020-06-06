package ir.coleo.alexa.models;

import java.io.Serializable;

/**
 * کلاس دیتا برای حالت های مختلف برای یک درخواست
 */
public class State implements Serializable {

    private String name;
    private String fontIconText;
    private int id;

    public State(String name, int id) {
        this.id = id;
        this.name = name;
        this.fontIconText = getFontIconText(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFontIconText() {
        return fontIconText;
    }

    private void setFontIconText(String fontIconText) {
        this.fontIconText = fontIconText;
    }

    private String getFontIconText(String name) {
        switch (id) {
            //for driver choose
            case 0: {
                setFontIconText("\uE806");
                break;
            }
            //wait driver arrived
            case 1: {
                setFontIconText("\uE806");
                break;
            }
            //driver arrived
            case 2: {
                setFontIconText("\uE806");
                break;
            }
            //"در خواست تکمیل شد"
            case 3: {
                setFontIconText("\uE80E");
                break;
            }
            //پرداخت شد
            case 4: {
                setFontIconText("\uE80E");
                break;
            }
            default: {
                setFontIconText("\uE80E");
                break;
            }
        }
        return getFontIconText();
    }
}
