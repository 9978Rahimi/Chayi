package ir.coleo.chayi.data_models;

import java.util.ArrayList;

public class Function {

    private String name;
    private boolean needToken;
    private boolean onItem;
    private FunctionType type;

    private ArrayList<Data> sendingData;
    private ArrayList<Data> receiveData;

    public Function(String name, boolean needToken, boolean onItem, FunctionType type, ArrayList<Data> sendingData, ArrayList<Data> receiveData) {
        this.name = name;
        this.needToken = needToken;
        this.onItem = onItem;
        this.type = type;
        this.sendingData = sendingData;
        this.receiveData = receiveData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeedToken() {
        return needToken;
    }

    public void setNeedToken(boolean needToken) {
        this.needToken = needToken;
    }

    public boolean isOnItem() {
        return onItem;
    }

    public void setOnItem(boolean onItem) {
        this.onItem = onItem;
    }

    public FunctionType getType() {
        return type;
    }

    public void setType(FunctionType type) {
        this.type = type;
    }

    public ArrayList<Data> getSendingData() {
        return sendingData;
    }

    public void setSendingData(ArrayList<Data> sendingData) {
        this.sendingData = sendingData;
    }

    public ArrayList<Data> getReceiveData() {
        return receiveData;
    }

    public void setReceiveData(ArrayList<Data> receiveData) {
        this.receiveData = receiveData;
    }
}
