package ir.coleo.alexa.models.android;

import ir.coleo.alexa.models.CitizenRequestWaste;

public class Waste extends CitizenRequestWaste {

    public Waste(ir.coleo.alexa.models.Waste waste) {
        super();
        super.waste = waste;
    }

    public double getWeight() {
        return citizen_amount;
    }

    public void setWeight(double weight) {
        super.citizen_amount = weight;
    }

    public double addWeight() {
        citizen_amount += 0.5;
        return citizen_amount;
    }

    public double subWeight() {
        citizen_amount -= 0.5;
        if (citizen_amount < 0) {
            citizen_amount = 0;
        }
        return citizen_amount;
    }

    public static String getWasteWeight(double weight) {
        if (weight < 0.1) {
            return "۰ کیلو";
        } else if ((int) weight == weight) {
            return String.format("%.0f کیلو", weight);
        } else
            return String.format("%.1f کیلو", weight);
    }

}
