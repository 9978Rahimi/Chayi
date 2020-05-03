//package ir.coleo.alexa.testing_models;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
//
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//
//public class Citizen extends Chayi {
//
//    public static String urlAll = "citizens";
//
//    String phone;
//    String code;
//    String introduceCode = "";
//
//    public Citizen(String phone) {
//        this.phone = phone;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    @Override
//    public String getUrl() {
//        return urlAll + "/" + getId();
//    }
//
//    @Override
//    public RequestBody getJsonObject() {
//        return RequestBody.create(MediaType.parse("json"), RetrofitSingleTone.getInstance().getGson().toJson(this));
//    }
//
//    public RequestBody app_enter() {
//        JsonObject object = new JsonObject();
//        object.add("phone", new JsonPrimitive(phone));
//        return RequestBody.create(MediaType.parse("json"), object.toString());
//    }
//
//    public RequestBody app_check_code() {
//        JsonObject citizen = new JsonObject();
//        citizen.add("phone", new JsonPrimitive(phone));
//        citizen.add("code", new JsonPrimitive(code));
//        citizen.add("introduce_code", new JsonPrimitive(introduceCode));
////        JsonObject object = new JsonObject();
////        object.add("citizen", citizen);
//        return RequestBody.create(MediaType.parse("json"), citizen.toString());
//    }
//
//}
