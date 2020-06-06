package ir.coleo.alexa.models;

import android.util.Pair;

import java.util.ArrayList;

public class WasteManager {

    ArrayList<WasteCategory> categories;
    ArrayList<Waste> wastes;
    ArrayList<Pair<WasteCategory, ArrayList<ir.coleo.alexa.models.android.Waste>>> data = new ArrayList<>();
    private static WasteManager instance;

    private WasteManager() {

    }

    public static WasteManager getInstance() {
        if (instance == null) {
            instance = new WasteManager();
        }
        return instance;
    }

    public void reset() {
        for (Pair<WasteCategory, ArrayList<ir.coleo.alexa.models.android.Waste>> p : data) {
            for (ir.coleo.alexa.models.android.Waste waste : p.second) {
                waste.setWeight(0);
            }
        }
    }

    public void setCategories(ArrayList<WasteCategory> categories) {
        this.categories = categories;
    }

    public void setWastes(ArrayList<Waste> wastes) {
        this.wastes = wastes;
    }

    public ArrayList<WasteCategory> getCategories() {
        return categories;
    }

    public ArrayList<ir.coleo.alexa.models.android.Waste> getWastes(WasteCategory wasteCategory) {
        for (Pair<WasteCategory, ArrayList<ir.coleo.alexa.models.android.Waste>> p : data) {
            if (p.first.equals(wasteCategory)) {
                return p.second;
            }
        }
        ArrayList<ir.coleo.alexa.models.android.Waste> wastes = new ArrayList<>();
        for (Waste waste : this.wastes) {
            if (waste.getCategory().equals(wasteCategory)) {
                wastes.add(new ir.coleo.alexa.models.android.Waste(waste));
            }
        }
        data.add(new Pair<>(wasteCategory, wastes));
        return wastes;
    }

    public double getSumOfWastes(WasteCategory wasteCategory) {
        for (Pair<WasteCategory, ArrayList<ir.coleo.alexa.models.android.Waste>> p : data) {
            if (p.first.equals(wasteCategory)) {
                double total = 0;
                for (ir.coleo.alexa.models.android.Waste waste : p.second) {
                    total += waste.getWeight();
                }
                return total;
            }
        }
        return 0;
    }

    public boolean isPositive() {
        double total = 0;
        for (Pair<WasteCategory, ArrayList<ir.coleo.alexa.models.android.Waste>> p : data) {
            for (ir.coleo.alexa.models.android.Waste waste : p.second) {
                total += waste.getWeight();
            }
        }
        return total > 0;
    }

    public ArrayList<ir.coleo.alexa.models.android.Waste> getAllWeighted() {
        ArrayList<ir.coleo.alexa.models.android.Waste> all = new ArrayList<>();
        for (Pair<WasteCategory, ArrayList<ir.coleo.alexa.models.android.Waste>> p : data) {
            for (ir.coleo.alexa.models.android.Waste waste : p.second) {
                if (waste.getWeight() > 0) {
                    all.add(waste);
                }
            }
        }
        return all;
    }

}
