package ir.coleo.alexa.models;

public class WasteOffline {

    String title;
    int image;
    int weight;

    public WasteOffline(String title, int image, int weight) {
        this.title = title;
        this.image = image;
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public int getImage_id() {
        return image;
    }

    public int getWeight() {
        return weight;
    }
}
