package ir.coleo.alexa.chayi;

/***
 *
 */
public class Cruder {

    private static Cruder cruder;

    private Cruder() {

    }

    public static Cruder getCruder() {
        if (cruder == null) {
            cruder = new Cruder();
        }
        return cruder;
    }


}
