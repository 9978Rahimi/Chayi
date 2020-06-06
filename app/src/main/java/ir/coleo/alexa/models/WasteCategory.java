package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WasteCategory extends Chayi {

    @Expose
    protected String title;
    @Expose
    protected Image icon;
    @Expose
    protected Integer order;
    @Expose(serialize = false)
    protected DateTime date_created;

    public WasteCategory() {
    }

    public WasteCategory(WasteCategory waste_category) {
        this.id = waste_category.id;
        this.title = waste_category.title;
        this.icon = waste_category.icon;
        this.order = waste_category.order;
        this.date_created = waste_category.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean create_on_item = false;


    public static RequestBody create_request(String title, Image icon, Integer order) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("waste_category", object);
        object.add("title", new JsonPrimitive(title));
        if (icon == null) object.add("icon", null);
        else {
            JsonObject icon_object = new JsonObject();
            icon_object.add("id", new JsonPrimitive(icon.getId()));
            object.add("icon", icon_object);
        }
        object.add("order", new JsonPrimitive(order));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
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
        return "waste_categories";
    }

    public static String getSingleName() {
        return "waste_category";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WasteCategory temp = (WasteCategory) o;
        return this.id == temp.id;
    }
}