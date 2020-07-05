package ir.coleo.chayi.data_models.data_type;

public class ListDataType extends DataType {

    private DataType type;

    public ListDataType(DataType type) {
        this.type = type;
    }

    public DataType getType() {
        return type;
    }

}
