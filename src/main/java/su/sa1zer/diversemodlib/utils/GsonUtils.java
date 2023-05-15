package su.sa1zer.diversemodlib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class GsonUtils {

    public static final Gson GSON = new GsonBuilder().serializeNulls()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting().create();

    public static final JsonParser JSON_PARSER = new JsonParser();

    public static <T> T toObject(String json, Class<T> clazz) {
        return (T) GSON.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static JsonArray array(String... values) {
        JsonArray jsonArray = new JsonArray();
        for(String s : values) {
            jsonArray.add(s);
        }
        return jsonArray;
    }
}
