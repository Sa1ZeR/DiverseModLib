package su.sa1zer.diversemodlib.data.gson;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Type;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String[] itemName = jsonObject.get("item").getAsString().split(":");
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName[0], itemName[1]));

        ItemStack stack = new ItemStack(item);

        stack.setCount(jsonObject.get("amount").getAsInt());
        stack.setDamageValue(jsonObject.get("damage").getAsInt());
        if(jsonObject.has("nbt"))
            stack.setTag(context.deserialize(jsonObject.get("nbt"), TagAdapter.TYPE));

        return stack;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        ResourceLocation key = ForgeRegistries.ITEMS.getKey(src.getItem());

        if(key == null) throw new IllegalArgumentException("Can't find item with " + src.getItem());

        jsonObject.addProperty("item", key.toString());
        jsonObject.addProperty("amount", src.getCount());
        jsonObject.addProperty("damage", src.getDamageValue());
        if(src.getTag() != null && !src.getTag().isEmpty())
            jsonObject.add("nbt", context.serialize(src.getTag()));

        return jsonObject;
    }
}
