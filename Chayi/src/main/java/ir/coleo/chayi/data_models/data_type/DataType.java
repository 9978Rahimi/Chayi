package ir.coleo.chayi.data_models.data_type;

public abstract class DataType {

    public static DataType builder(String input) {
        PrimitiveData data = PrimitiveData.getFromString(input);
        if (data != null) {
            return new PrimitiveDataType(data);
        } else {
            if (input.startsWith("list")) {
                String newInput = input.substring(5, input.length() - 1);
                return new ListDataType(DataType.builder(newInput));
            } else if (input.startsWith("class")) {
                String newInput = input.substring(6, input.length() - 1);
                return new ClassDataType(newInput);
            }
        }
        return null;
    }

}
