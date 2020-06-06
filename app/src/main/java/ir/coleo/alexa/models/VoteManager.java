package ir.coleo.alexa.models;

import java.util.ArrayList;

public class VoteManager {

    private static VoteManager instance;
    private ArrayList<VoteChoice> votes;
    private ArrayList<ArrayList<ir.coleo.alexa.models.android.VoteChoice>> list;

    private VoteManager() {

    }

    public static VoteManager getInstance() {
        if (instance == null) {
            instance = new VoteManager();
        }
        return instance;
    }

    public ArrayList<VoteChoice> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<VoteChoice> votes) {
        this.votes = votes;
    }

    public ArrayList<ArrayList<ir.coleo.alexa.models.android.VoteChoice>> getList() {
        if (list != null) {
            return list;
        }
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(new ArrayList<>());
        }
        for (VoteChoice choice : votes) {
            String[] starts = choice.getOnStars().split(",");
            for (String star : starts) {
                list.get(Integer.parseInt(star)).add(new ir.coleo.alexa.models.android.VoteChoice(choice));
            }
        }
        return list;
    }


}
