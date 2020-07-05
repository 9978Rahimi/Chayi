package ir.coleo.chayi.data_models;

public enum FunctionType {

    PUT, POST, DELETE, GET;

    public static FunctionType fromString(String input) {
        switch (input) {
            case "PUT": {
                return PUT;
            }
            case "POST": {
                return POST;
            }
            case "GET": {
                return GET;
            }
            case "DELETE": {
                return DELETE;
            }
        }
        return null;
    }

}
