package ir.coleo.alexa.models.android;

import android.graphics.Color;

import ir.coleo.alexa.models.CitizenRequest;

public class HistoryRequest extends CitizenRequest {

    public HistoryRequest(CitizenRequest citizenRequest) {
        super(citizenRequest);
        super.price = citizenRequest.price;
        super.wastes = citizenRequest.wastes;
        super.state = citizenRequest.state;
        super.text = citizenRequest.text;
    }

    public HistoryRequest(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return (new BeautifiedDate(super.date_created)).toString();
    }

    public String getPrice() {
        return super.price + " تومان";
    }

//    public int getFontIconText() {
//        switch (super.state) {
//            case 4: {
//                return R.string.complete_icon;
//            }
//            case -3:
//            case -2: {
//                return R.string.x_icon;
//            }
//            default: {
//                return R.string.progressing;
//            }
//        }
//    }

    public int getStateColor() {
        switch (super.state) {
            case 4: {
                return Color.rgb(0, 206, 125);
            }
            case -3:
            case -2: {
                return Color.rgb(255, 50, 50);
            }
            default: {
                return Color.rgb(255, 125, 0);
            }
        }
    }

    public String getStateString() {
        return text;
    }

}
