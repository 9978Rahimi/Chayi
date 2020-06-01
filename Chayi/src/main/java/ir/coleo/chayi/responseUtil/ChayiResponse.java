package ir.coleo.chayi.responseUtil;

import org.json.JSONObject;

public class ChayiResponse {

    boolean ok;
    JSONObject response;

    public ChayiResponse(boolean ok, JSONObject response) {
        this.ok = ok;
        this.response = response;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }
}
