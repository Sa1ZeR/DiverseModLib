package su.sa1zer.diversemodlib.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import su.sa1zer.diversemodlib.utils.GsonUtils;
import su.sa1zer.diversemodlib.utils.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseConfig {

    protected static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Path filePath;

    public BaseConfig(String dir, String fileName) {
        filePath = Paths.get(dir + "/" + fileName);
    }

    public void save() {
        String data = gson.toJson(getClass());
        IOUtils.writeToFile(filePath, data);
    }

    public BaseConfig read() {
        if(!Files.exists(filePath))
            save();
        try(FileReader fr = new FileReader(filePath.toFile())) {
            return gson.fromJson(fr, getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
