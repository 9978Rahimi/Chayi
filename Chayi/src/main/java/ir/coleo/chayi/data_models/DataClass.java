package ir.coleo.chayi.data_models;

import java.util.ArrayList;

public class DataClass {

    private final String className;
    private String singleName;
    private String pluralName;
    private ArrayList<Function> functions = new ArrayList<>();

    public DataClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void addFunction(Function function) {
        functions.add(function);
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public String getPluralName() {
        return pluralName;
    }

    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }

}
