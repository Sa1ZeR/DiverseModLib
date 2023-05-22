package su.sa1zer.diversemodlib.data.gson.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.resources.ResourceKey;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class LevelResourceKeyAdapter implements JsonSerializer<ResourceKey<Level>> {
    @Override
    public JsonElement serialize(ResourceKey<Level> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        obj.addProperty("world", src.toString());

        return obj;
    }
}
