package su.sa1zer.diversemodlib.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import su.sa1zer.diversemodlib.utils.GsonUtils;
import su.sa1zer.diversemodlib.utils.ResourcesUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class LangResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final JsonObject langObject;

    public LangResource(String lang, Class<?> modClass, String modId) {
        InputStream stream = modClass.getResourceAsStream("/assets/" + modId + "/lang/" + lang + ".json");
        File langFile = ResourcesUtils.getFileFromResources(stream);

        if(langFile == null)
            throw new RuntimeException("Lang file is null");

        try {
            List<String> strings = Files.readAllLines(langFile.toPath());
            StringBuilder stringBuilder = new StringBuilder();
            for(String s : strings) {
                stringBuilder.append(s);
            }

            langObject = GsonUtils.JSON_PARSER.parse(stringBuilder.toString())
                    .getAsJsonObject();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage(String path) {
        return langObject.get(path).getAsString();
    }
}
