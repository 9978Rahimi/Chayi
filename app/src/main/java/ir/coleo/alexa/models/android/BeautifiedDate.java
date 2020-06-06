package ir.coleo.alexa.models.android;

import ir.coleo.alexa.models.DateTime;

public class BeautifiedDate extends DateTime {

    public BeautifiedDate(DateTime dateTime) {
        super(dateTime);
    }

    @Override
    public String toString() {
        return String.format("%d:%d - %d/%d/%d", hour, minute, year, month, day);
    }
}
