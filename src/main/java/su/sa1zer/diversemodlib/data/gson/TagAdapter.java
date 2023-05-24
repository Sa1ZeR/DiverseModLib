package su.sa1zer.diversemodlib.data.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.nbt.*;

import java.lang.reflect.Type;
import java.util.Map;

public class TagAdapter implements JsonSerializer<Tag>, JsonDeserializer<Tag> {

    public static final Type TYPE = (new TypeToken<Tag>() {}).getType();

    @Override
    public Tag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull())
            return null;
        if (json.isJsonObject()) {
            CompoundTag compound = new CompoundTag();
            JsonObject object = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Tag nbtBase = context.deserialize(entry.getValue(), TYPE);
                compound.put(entry.getKey(), nbtBase);
            }
            return compound;
        }
        if (json.isJsonArray()) {
            ListTag nbtList = new ListTag();
            JsonArray jsonList = json.getAsJsonArray();
            for (JsonElement element : jsonList) {
                Tag nbtBase = context.deserialize(element, TYPE);
                nbtList.add(nbtBase);
            }
            return nbtList;
        }
        if (json.isJsonPrimitive()) {
            String string = json.getAsString();
            if (string.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]"))
                return DoubleTag.valueOf(Double.parseDouble(string.substring(0, string.length() - 1)));
            if (string.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]"))
                return FloatTag.valueOf(Float.parseFloat(string.substring(0, string.length() - 1)));
            if (string.matches("[-+]?[0-9]+[b|B]"))
                return ByteTag.valueOf(Byte.parseByte(string.substring(0, string.length() - 1)));
            if (string.matches("[-+]?[0-9]+[l|L]"))
                return LongTag.valueOf(Long.parseLong(string.substring(0, string.length() - 1)));
            if (string.matches("[-+]?[0-9]+[s|S]"))
                return ShortTag.valueOf(Short.parseShort(string.substring(0, string.length() - 1)));
            if (string.matches("[-+]?[0-9]+"))
                return IntTag.valueOf(Integer.parseInt(string.substring(0, string.length())));
            if (string.matches("[-+]?[0-9]*\\.?[0-9]+"))
                return DoubleTag.valueOf(Double.parseDouble(string.substring(0, string.length())));
            if (!string.equalsIgnoreCase("true") && !string.equalsIgnoreCase("false")) {
                if (string.startsWith("[") && string.endsWith("]")) {
                    if (string.length() > 2) {
                        String s = string.substring(1, string.length() - 1);
                        String[] astring = s.split(",");
                        try {
                            if (astring.length <= 1)
                                return new IntArrayTag(new int[] { Integer.parseInt(s.trim()) });
                            int[] aint = new int[astring.length];
                            for (int i = 0; i < astring.length; i++)
                                aint[i] = Integer.parseInt(astring[i].trim());
                            return new IntArrayTag(aint);
                        } catch (NumberFormatException numberformatexception) {
                            return StringTag.valueOf(string);
                        }
                    }
                    return new IntArrayTag(new int[0]);
                }
                if (string.startsWith("\"") && string.endsWith("\"") && string.length() > 2)
                    string = string.substring(1, string.length() - 1);
                string = string.replaceAll("\\\\\"", "\"");
                return StringTag.valueOf(string);
            }
            return ByteTag.valueOf((byte)(Boolean.parseBoolean(string) ? 1 : 0));
        }
        throw new JsonParseException("Invalid NBT: " + json.toString());
    }

    @Override
    public JsonElement serialize(Tag src, Type typeOfSrc, JsonSerializationContext context) {
        if(src instanceof StringTag)
            return new JsonPrimitive(src.getAsString());
        if(src instanceof NumericTag t)
            return new JsonPrimitive(t.getAsNumber());
        if(src instanceof ByteArrayTag t) {
            JsonArray jsonArray = new JsonArray();
            for(byte b : t.getAsByteArray()) {
                jsonArray.add(b);
            }
            return jsonArray;
        }
        if(src instanceof IntArrayTag t) {
            JsonArray jsonArray = new JsonArray();
            for(int i : t.getAsIntArray()) {
                jsonArray.add(i);
            }
            return jsonArray;
        }
        if(src instanceof LongArrayTag t) {
            JsonArray jsonArray = new JsonArray();
            for(long l : t.getAsLongArray()) {
                jsonArray.add(l);
            }
            return jsonArray;
        }
        if(src instanceof ListTag t) {
            JsonArray array = new JsonArray();
            for (Tag tag : t)
                array.add(context.serialize(tag));
            return array;
        }
        if(src instanceof CompoundTag t) {
            JsonObject object = new JsonObject();
            for (String name : t.getAllKeys())
                object.add(name, context.serialize(t.get(name)));
            return object;
        }
        throw new IllegalArgumentException("Not supported JSON code. Type: " + src.getClass() + " Value: " + src);
    }
}
