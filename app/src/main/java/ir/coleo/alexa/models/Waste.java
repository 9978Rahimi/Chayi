package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Waste extends Chayi {

    @Expose(serialize = false)
    protected WasteCategory category;
    @Expose(serialize = false)
    protected String title;
    @Expose(serialize = false)
    protected Integer price;
    @Expose(serialize = false)
    protected Integer order;
    @Expose(serialize = false)
    protected DateTime date_created;

    public Waste() {
    }

    public Waste(Waste waste) {
        this.id = waste.id;
        this.category = waste.category;
        this.title = waste.title;
        this.price = waste.price;
        this.order = waste.order;
        this.date_created = waste.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(String title, WasteCategory category, Integer price, Integer order) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("waste", object);
        object.add("title", new JsonPrimitive(title));
        if (category == null) object.add("category", null);
        else {
            JsonObject category_object = new JsonObject();
            category_object.add("id", new JsonPrimitive(category.getId()));
            object.add("category", category_object);
        }
        object.add("price", new JsonPrimitive(price));
        object.add("order", new JsonPrimitive(order));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public WasteCategory getCategory() {
        return category;
    }

    public void setCategory(WasteCategory category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }


    public static String getPluralName() {
        return "wastes";
    }

    public static String getSingleName() {
        return "waste";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waste temp = (Waste) o;
        return this.id == temp.id;
    }
}