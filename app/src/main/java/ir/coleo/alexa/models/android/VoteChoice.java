package ir.coleo.alexa.models.android;

public class VoteChoice extends ir.coleo.alexa.models.VoteChoice {

    private boolean selected = false;

    public VoteChoice(ir.coleo.alexa.models.VoteChoice vote_choice) {
        super(vote_choice);
    }

    public void select() {
        selected = !selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
