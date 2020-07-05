package ir.coleo.chayi.data_models.data_type;

public enum PrimitiveData {

    String, Integer, Boolean, Float;


    public static PrimitiveData getFromString(String input) {
        switch (input) {
            case "String": {
                return String;
            }
            case "Boolean": {
                return Boolean;
            }
            case "Integer": {
                return Integer;
            }
            case "Float": {
                return Float;
            }
        }
        return null;
    }


}
