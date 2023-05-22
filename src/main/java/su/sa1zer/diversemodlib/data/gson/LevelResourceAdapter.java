package su.sa1zer.diversemodlib.data.gson;

import com.google.gson.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;

import java.lang.reflect.Type;

public class LevelResourceAdapter implements JsonDeserializer<ResourceKey<Level>>, JsonSerializer<ResourceKey<Level>> {
    @Override
    public ResourceKey<Level> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String world = json.getAsJsonObject().get("world").getAsString();
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(world));
    }


    @Override
    public JsonElement serialize(ResourceKey<Level> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        obj.addProperty("world", src.toString());

        return obj;
    }
}
