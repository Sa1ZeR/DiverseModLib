package su.sa1zer.diversemodlib.data.gson;

import com.google.gson.*;
import net.minecraft.core.BlockPos;

import java.lang.reflect.Type;

public class BlockPosAdapter implements JsonDeserializer<BlockPos>, JsonSerializer<BlockPos> {
    @Override
    public BlockPos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int x = obj.get("x").getAsInt();
        int y = obj.get("y").getAsInt();
        int z = obj.get("z").getAsInt();

        return new BlockPos(x, y, z);
    }

    @Override
    public JsonElement serialize(BlockPos src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", src.getX());
        jsonObject.addProperty("y", src.getY());
        jsonObject.addProperty("z", src.getZ());

        return jsonObject;
    }
}
