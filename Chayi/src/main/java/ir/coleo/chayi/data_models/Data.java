package ir.coleo.chayi.data_models;

import ir.coleo.chayi.data_models.data_type.DataType;

public class Data {

    private String name;
    private DataType type;

    private Data(String name, DataType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }


    public static Data builder(String key, String value) {
        return new Data(key, DataType.builder(value));
    }

}
