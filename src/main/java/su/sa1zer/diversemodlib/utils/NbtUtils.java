package su.sa1zer.diversemodlib.utils;

import com.google.gson.*;
import net.minecraft.nbt.*;

import java.util.Map;

public class NbtUtils {

    public static Tag toNbt(JsonElement jsonElement) {
        // JSON Primitive
        if (jsonElement instanceof JsonPrimitive jsonPrimitive) {
            if (jsonPrimitive.isBoolean()) {
                boolean value = jsonPrimitive.getAsBoolean();
                if (value) {
                    return ByteTag.valueOf(true);
                } else {
                    return ByteTag.valueOf(false);
                }
            } else if (jsonPrimitive.isNumber()) {
                Number number = jsonPrimitive.getAsNumber();

                if (number instanceof Byte) {
                    return ByteTag.valueOf(number.byteValue());
                } else if (number instanceof Short) {
                    return ShortTag.valueOf(number.shortValue());
                } else if (number instanceof Integer) {
                    return IntTag.valueOf(number.intValue());
                } else if (number instanceof Long) {
                    return LongTag.valueOf(number.longValue());
                } else if (number instanceof Float) {
                    return FloatTag.valueOf(number.floatValue());
                } else if (number instanceof Double) {
                    return DoubleTag.valueOf(number.doubleValue());
                }

            } else if (jsonPrimitive.isString()) {
                return StringTag.valueOf(jsonPrimitive.getAsString());
            }

            // JSON Array
        } else if (jsonElement instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray) jsonElement;
            ListTag nbtList = new ListTag();

            for (JsonElement element : jsonArray) {
                nbtList.add(toNbt(element));
            }

            return nbtList;

            // JSON Object
        } else if (jsonElement instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            CompoundTag nbtCompound = new CompoundTag();

            for (Map.Entry<String, JsonElement> jsonEntry : jsonObject.entrySet()) {
                nbtCompound.put(jsonEntry.getKey(), toNbt(jsonEntry.getValue()));
            }

            return nbtCompound;

            // Null - Not fully supported
        } else if (jsonElement instanceof JsonNull) {
            return new CompoundTag();
        }

        // Something has gone wrong, throw an error.
        throw new AssertionError();
    }

    public static JsonElement toJson(Tag nbtElement) {
        return toJson(nbtElement, ConversionMode.RAW);
    }

    /**
     * Converts an NBT tag to a JSON element.
     *
     * @param nbtElement NBT tag to convert.
     * @param mode The conversion mode.
     * @return The JSON element equivalent. (imperfect in certain cases)
     */
    @SuppressWarnings("unchecked")
    public static JsonElement toJson(Tag nbtElement, ConversionMode mode) {
        // Numbers
        if (nbtElement instanceof NumericTag nbtNumber) {
            switch (mode) {
                case JSON: {
                    if (nbtNumber instanceof ByteTag nbtByte) {
                        byte value = nbtByte.getAsByte();

                        switch (value) {
                            case 0: return new JsonPrimitive(false);
                            case 1: return new JsonPrimitive(true);

                            default: // Continue
                        }
                    }

                    // Else, continue
                }

                case RAW: {
                    return new JsonPrimitive(nbtNumber.getAsNumber());
                }
            }

            // String
        } else if (nbtElement instanceof StringTag nbtString) {
            return new JsonPrimitive(nbtString.getAsString());

            // Lists
        } else if (nbtElement instanceof ListTag nbtList) {
            JsonArray jsonArray = new JsonArray();

            for (Tag nbtBase : nbtList) {
                jsonArray.add(toJson(nbtBase, mode));
            }

            return jsonArray;

            // Compound tag
        } else if (nbtElement instanceof CompoundTag nbtCompound)  {
            JsonObject jsonObject = new JsonObject();

            for (String key : nbtCompound.getAllKeys()) {
                jsonObject.add(key, toJson(nbtCompound.get(key), mode));
            }

            return jsonObject;

            // Nbt termination tag. Should not be encountered.
        }

        // Impossible unless a new NBT class is made.
        throw new UnsupportedOperationException();
    }

    /**
     * The NBT to JSON conversion mode.
     */
    public enum ConversionMode {

        /**
         * The NBT will be converted to JSON 'as is'.
         */
        RAW,
        /**
         * The NBT will be converted to JSOn with the assumption that it
         * was previously JSON, and therefore certain assumptions and
         * conversions will be made.
         *
         * Conversions:
         *
         * 0b -> false
         * 1b -> true
         */
        JSON
    }
}
