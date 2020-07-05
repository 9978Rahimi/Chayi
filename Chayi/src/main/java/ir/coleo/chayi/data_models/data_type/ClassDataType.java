package ir.coleo.chayi.data_models.data_type;

public class ClassDataType extends DataType{

    private String className;

    public ClassDataType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
