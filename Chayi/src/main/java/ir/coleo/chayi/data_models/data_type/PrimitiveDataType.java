package ir.coleo.chayi.data_models.data_type;

public class PrimitiveDataType extends DataType {

    final private PrimitiveData data;

    public PrimitiveDataType(PrimitiveData data) {
        this.data = data;
    }

    public PrimitiveData getData() {
        return data;
    }

}


