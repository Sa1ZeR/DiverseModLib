package su.sa1zer.diversemodlib.data.gson;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import su.sa1zer.diversemodlib.MainMod;

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
        if(jsonObject.has("nbt")) {
            try {
                CompoundTag nbt = TagParser.parseTag(jsonObject.get("nbt").getAsString());
                stack.setTag(nbt);
            } catch (CommandSyntaxException e) {
                MainMod.LOGGER.error("Cant parse nbt {}", jsonObject);
            }
        }

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
            jsonObject.addProperty("nbt", src.getTag().toString());

        return jsonObject;
    }
}
