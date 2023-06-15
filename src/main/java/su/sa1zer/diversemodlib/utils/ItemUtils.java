package su.sa1zer.diversemodlib.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {

    public static boolean canRepair(ItemStack stack) {
        return !stack.equals(ItemStack.EMPTY) && stack.isDamageableItem() &&
                (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof TieredItem || stack.getItem() instanceof BowItem);
    }

    public static ItemStack createItem(String itemId, int count) {
        String[] itemIdData = itemId.split(":");

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemIdData[0], itemIdData[1]));

        boolean notValid = false;
        if (item == null || item.equals(Items.AIR)) {
            item = Items.APPLE;
            notValid = true;
        }

        ItemStack itemStack = new ItemStack(item, count);
        if (itemIdData.length == 3) {
            itemStack.setDamageValue(Integer.parseInt(itemIdData[2]));
        }

        if (notValid) {
            itemStack.setHoverName(Component.translatable("§4Произошла ошибка, обратитесь к администрации, приложив скриншот предмета!"));
            addLore(itemStack, "§cНеверное id предмета: " + itemId);
        }

        return itemStack;
    }

    public static void addLore(ItemStack stack, String... loreArray) {
        addLore(stack, Arrays.asList(loreArray));
    }

    public static void addLore(ItemStack stack, List<String> loreList) {
        CompoundTag tags = stack.getOrCreateTag();

        CompoundTag display = tags.getCompound("display");
        ListTag lore = display.getList("Lore", Tag.TAG_STRING);
        for(String s : loreList) {
            lore.add(StringTag.valueOf(s));
        }

        display.put("Lore", lore);
        tags.put("display", display);
        stack.setTag(tags);
    }
}
