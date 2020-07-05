package ir.coleo.chayi.pipline;

import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.data_models.Data;
import ir.coleo.chayi.data_models.DataClass;
import ir.coleo.chayi.data_models.Function;
import ir.coleo.chayi.data_models.FunctionType;

public class ChayiJsonParser {

    private static final String TAG = ChayiJsonParser.class.getSimpleName();
    private static ChayiJsonParser chayiJsonParser;
    private ArrayList<DataClass> dataClasses;

    private ChayiJsonParser() {
        dataClasses = checkJson();
    }

    public static ChayiJsonParser getInstance() {
        if (chayiJsonParser == null) {
            chayiJsonParser = new ChayiJsonParser();
        }
        return chayiJsonParser;
    }

    @Nullable
    private ArrayList<DataClass> checkJson() {
        JSONObject jsonObject = readJsonObjectFromFile();
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONObject header = jsonObject.getJSONObject("chayi");
            ArrayList<DataClass> dataClasses = parseHeader(header);
            if (dataClasses == null) {
                return null;
            }
            JSONObject body = jsonObject.getJSONObject("classes");
            parseBody(dataClasses, body);
            return dataClasses;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseBody(@NotNull ArrayList<DataClass> dataClasses, JSONObject body) {
        try {
            for (DataClass dataClass : dataClasses) {
                JSONObject classJsonObject = body.getJSONObject(dataClass.getClassName());

                dataClass.setPluralName(classJsonObject.getString("plural_name"));
                dataClass.setSingleName(classJsonObject.getString("single_name"));

                JSONArray functions = classJsonObject.getJSONArray("functions");
                for (int i = 0; i < functions.length(); i++) {
                    String functionName = (String) functions.get(i);
                    JSONObject temp = classJsonObject.getJSONObject(functionName);
                    dataClass.addFunction(parseFunction(temp, functionName));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Function parseFunction(JSONObject json, String name) {
        Function function = null;
        try {
            FunctionType type = FunctionType.fromString(json.getString("type"));
            boolean token = json.getBoolean("need_token");
            boolean item = json.getBoolean("on_item");
            ArrayList<Data> sendingData = new ArrayList<>();
            ArrayList<Data> receiveData = new ArrayList<>();

            JSONObject sending = json.getJSONObject("sending_data");
            Iterator<String> keys = sending.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                sendingData.add(Data.builder(key, sending.getString(key)));
            }

            JSONObject receiving = json.getJSONObject("receive_data");
            keys = sending.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                receiveData.add(Data.builder(key, sending.getString(key)));
            }

            function = new Function(name, token, item, type, sendingData, receiveData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return function;
    }

    @Nullable
    private ArrayList<DataClass> parseHeader(@NotNull JSONObject header) {
        try {
            String versionName = header.getString("version_name");
            if (!versionName.equalsIgnoreCase(Constants.VERSION_NAME)) {
                Log.e(TAG, "parseHeader: Chayi version name dose not matches");
                return null;
            }

            int versionCode = header.getInt("version_code");
            if (versionCode != Constants.VERSION_CODE) {
                Log.e(TAG, "parseHeader: Chayi version code dose not matches");
                return null;
            }

            String baseUrl = header.getString("base_url");
            if (!baseUrl.isEmpty()) {
                Constants.setBase_url(baseUrl);
            }

            JSONArray classes = header.getJSONArray("classes");
            ArrayList<DataClass> dataClasses = new ArrayList<>();
            for (int i = 0; i < classes.length(); i++) {
                dataClasses.add(new DataClass((String) classes.get(i)));
            }
            return dataClasses;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private JSONObject readJsonObjectFromFile() {
        File file;
        if (PipLine.isDebug()) {
            file = new File("../../../app/src/debug/res/raw/chayi.json");
        } else {
            file = new File("../../../app/src/main/res/raw/chayi.json");
        }
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(file));
            String string = "";
            StringBuilder output = new StringBuilder();
            while ((string = inputStream.readLine()) != null) {
                output.append(string);
            }
            return new JSONObject(output.toString());
        } catch (FileNotFoundException e) {
            Log.e(TAG, "checkJson: Json file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<DataClass> getDataClasses() {
        return dataClasses;
    }
}
