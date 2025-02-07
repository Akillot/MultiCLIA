package core.logic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.insertControlChars;
import static core.configs.TextConfigs.message;
import static java.lang.System.out;

public class JsonManager {
    private static final String FILE_PATH = "config.json";
    private static JSONObject jsonData = new JSONObject();

    static {
        loadJson();
    }

    private static void loadJson() {
        try (InputStream is = new FileInputStream(FILE_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            jsonData = new JSONObject(new JSONTokener(reader));
        } catch (IOException e) {
            insertControlChars('n', 1);
            message("Error loading JSON: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    public static @Nullable Object get(@NotNull String path) {
        String[] keys = path.split("/");
        JSONObject obj = jsonData;
        for (int i = 0; i < keys.length - 1; i++) {
            if (!obj.has(keys[i]) || !(obj.get(keys[i]) instanceof JSONObject)) {
                return null;
            }
            obj = obj.getJSONObject(keys[i]);
        }
        return obj.opt(keys[keys.length - 1]);
    }

    public static int getInt(String path) {
        Object value = get(path);
        return (value instanceof Number) ? ((Number) value).intValue() : 0;
    }

    public static List<String> getStringList(String path) {
        Object value = get(path);
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            return IntStream.range(0, array.length())
                    .mapToObj(array::getString)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public static void updateJson(@NotNull String path, Object value) {
        String[] keys = path.split("/");
        JSONObject obj = jsonData;

        for (int i = 0; i < keys.length - 1; i++) {
            obj = obj.optJSONObject(keys[i]);
            if (obj == null) return;
        }

        if (value instanceof Map) {
            Map<String, Integer> map = (Map<String, Integer>) value;
            obj.put(keys[keys.length - 1], new JSONObject(map));
        } else {
            obj.put(keys[keys.length - 1], value);
        }

        saveJson();
    }


    private static void saveJson() {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonData.toString(4));
            file.flush();
        } catch (IOException e) {
            insertControlChars('n', 1);
            message("Error saving JSON: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }
}
