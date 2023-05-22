package su.sa1zer.diversemodlib.data.gson.deserialise;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;

import java.lang.reflect.Type;

public class LevelResourceAdapter implements JsonDeserializer<ResourceKey<Level>> {
    @Override
    public ResourceKey<Level> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String world = json.getAsJsonObject().get("world").getAsString();
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(world));
    }
}
