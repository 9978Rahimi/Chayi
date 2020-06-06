package ir.coleo.alexa.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import ir.coleo.chayi.Chayi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Transaction extends Chayi {

    @Expose
    protected String amount_currency;
    @Expose
    protected Integer amount;
    @Expose(serialize = false)
    protected DateTime date_created;
    @Expose(serialize = false)
    public String title;

    public Transaction() {
    }

    public Transaction(Transaction transaction) {
        this.id = transaction.id;
        this.amount_currency = transaction.amount_currency;
        this.amount = transaction.amount;
        this.date_created = transaction.date_created;
    }

    public static final boolean create_token = true;
    public static final boolean my_objects_token = true;
    public static final boolean my_credit_token = true;
    public static final boolean create_on_item = false;
    public static final boolean my_objects_on_item = false;
    public static final boolean my_credit_on_item = false;


    public static RequestBody create_request(Integer amount) {
        JsonObject wrapper = new JsonObject();
        JsonObject object = new JsonObject();
        wrapper.add("transaction", object);
        object.add("amount", new JsonPrimitive(amount));
        object = wrapper;
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody my_objects_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public static RequestBody my_credit_request() {
        JsonObject object = new JsonObject();
        return RequestBody.create(MediaType.parse("json"), object.toString());
    }

    public String getAmountCurrency() {
        return amount_currency;
    }

    public void setAmountCurrency(String amount_currency) {
        this.amount_currency = amount_currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public DateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(DateTime date_created) {
        this.date_created = date_created;
    }


    public static String getPluralName() {
        return "transactions";
    }

    public static String getSingleName() {
        return "transaction";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction temp = (Transaction) o;
        return this.id == temp.id;
    }
}