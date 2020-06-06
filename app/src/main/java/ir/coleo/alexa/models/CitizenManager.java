package ir.coleo.alexa.models;

public class CitizenManager {

    private static CitizenManager instance;
    private Citizen citizen;

    private CitizenManager() {

    }

    public static CitizenManager getInstance() {
        if (instance == null) {
            instance = new CitizenManager();
        }
        return instance;
    }


    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }
}
