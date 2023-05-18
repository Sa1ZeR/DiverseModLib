package su.sa1zer.diversemodlib.utils;

import net.minecraft.world.item.*;

public class ItemUtils {

    public static boolean canRepair(ItemStack stack) {
        return !stack.equals(ItemStack.EMPTY) && stack.isDamageableItem() &&
                (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof TieredItem || stack.getItem() instanceof BowItem);
    }
}
