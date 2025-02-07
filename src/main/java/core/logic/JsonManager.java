package core.logic;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.insertControlChars;
import static core.configs.TextConfigs.message;
import static java.lang.System.out;

public class JsonManager {
    private final String filePath;
    private JSONObject jsonData;

    public JsonManager(String filePath) {
        this.filePath = filePath;
        loadJson();
    }

    private void loadJson() {
        try (InputStream is = new FileInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            jsonData = new JSONObject(new JSONTokener(reader));
        } catch (IOException e) {
            insertControlChars('n',1);
            message("Error: " + e.getMessage(),sysLayoutColor,getDefaultTextAlignment(),0,out::println);
            jsonData = new JSONObject();
        }
    }

    public Object get(String key) {
        return jsonData.opt(key);
    }

    public void set(String key, Object value) {
        jsonData.put(key, value);
        saveJson();
    }

    public void remove(String key) {
        jsonData.remove(key);
        saveJson();
    }

    private void saveJson() {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonData.toString(4));
            file.flush();
        } catch (IOException e) {

            message("Error: " + e.getMessage(),sysLayoutColor,getDefaultTextAlignment(),0,out::println);
        }
    }
}
