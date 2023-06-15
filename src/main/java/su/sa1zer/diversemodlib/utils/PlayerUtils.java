package su.sa1zer.diversemodlib.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import su.sa1zer.diversemodlib.MainMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class PlayerUtils {

    public static ServerPlayer getPlayer(UUID uuid) {
        return ServerUtils.getServer().getPlayerList().getPlayer(uuid);
    }

    public static ServerPlayer getPlayer(String name) {
        return ServerUtils.getServer().getPlayerList().getPlayerByName(name);
    }

    public static List<ServerPlayer> getNearbyPlayers(ServerPlayer player, int distance) {
        List<ServerPlayer> players = new ArrayList<>();

        PlayerList playerList = ServerUtils.getServer().getPlayerList();
        for(ServerPlayer p : playerList.getPlayers()) {
            if(p.getGameProfile().equals(player.getGameProfile())) continue;
            if(p.getLevel().equals(player.getLevel()) && p.distanceTo(player) <= distance)
                players.add(p);
        }

        return players;
    }

    public static void teleportPlayer(ServerPlayer player, Player toPlayer) {
        teleportPlayer(player, toPlayer.level.dimension(), toPlayer.blockPosition());
    }

    public static void teleportPlayer(ServerPlayer player, ResourceKey<Level> level, BlockPos pos) {
        teleportPlayer(player, level, pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, player.getYRot(), player.getXRot());
    }

    public static void teleportPlayer(ServerPlayer player, ResourceKey<Level> level, BlockPos pos, float yRot, float xRot) {
        teleportPlayer(player, level, pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, yRot, xRot);
    }

    public static void teleportPlayer(ServerPlayer player, ResourceKey<Level> level,
                                      double x, double y , double z, float yRot, float xRot) {
        ServerLevel world = player.server.getLevel(level);

        if (world == null) {
            MainMod.LOGGER.warn("World {} not found", world);
            return;
        }

        int lvl = player.experienceLevel;
        player.teleportTo(world, x, y, z, yRot, xRot);
        player.setExperienceLevels(lvl);
    }

    public void giveItemsSafely(Player player, ItemStack item) {
        giveItemsSafely(player, List.of(item));
    }

    public void giveItemsSafely(Player player, List<ItemStack> items) {
//        List<ItemStack> overSize = new ArrayList<>();

        int freeSlotsAmount = Math.min(getFreeSlotsAmount(player), items.size());
        int i;
        for(i = 0; i < freeSlotsAmount; i++) {
            ItemStack itemStack = items.get(i);
//            if(itemStack.getCount() > 64) {
//                ItemStack over = itemStack.copy();
//                int size = itemStack.getCount() - 64;
//                over.setCount(size);
//                overSize.add(over);
//
//                itemStack.setCount(itemStack.getCount() - size);
//            }

            player.addItem(itemStack.copy());
        }

        boolean isWarn = i < items.size();
        for(;i < items.size(); i++) {
            ItemStack itemStack = items.get(i);

            BlockPos pos = player.blockPosition();
            player.level.addFreshEntity(new ItemEntity(player.level, pos.getX(), pos.getY() + 1, pos.getZ(), itemStack));
        }

        if(isWarn) {
            MessageUtils.sendChatMessage(player,
                    MainMod.langResource.getMessage("full-inventory-waring"));
        }
    }

    public static boolean isFullInventory(Player player) {
        for(int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if(itemStack.equals(ItemStack.EMPTY)) return false;
        }
        return true;
    }

    public static int getFreeSlotsAmount(Player player) {
        int count = 0;
        for(int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if(itemStack.equals(ItemStack.EMPTY)) count++;
        }
        return count;
    }
}
