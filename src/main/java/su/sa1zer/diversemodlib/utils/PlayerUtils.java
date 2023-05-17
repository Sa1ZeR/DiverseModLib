package su.sa1zer.diversemodlib.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import su.sa1zer.diversemodlib.MainMod;

import java.util.UUID;

@UtilityClass
public class PlayerUtils {

    public static ServerPlayer getPlayer(UUID uuid) {
        return ServerUtils.getServer().getPlayerList().getPlayer(uuid);
    }

    public static ServerPlayer getPlayer(String name) {
        return ServerUtils.getServer().getPlayerList().getPlayerByName(name);
    }

    public static void teleportPlayer(ServerPlayer player, Player toPlayer) {
        teleportPlayer(player, toPlayer.level.dimension(), toPlayer.blockPosition());
    }

    public static void teleportPlayer(ServerPlayer player, ResourceKey<Level> level, BlockPos pos) {
        teleportPlayer(player, level, pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D);
    }

    public static void teleportPlayer(ServerPlayer player, ResourceKey<Level> level,
                                      double x, double y , double z) {
        ServerLevel world = player.server.getLevel(level);

        if (world == null) {
            MainMod.LOGGER.warn("World {} not found", world);
            return;
        }

        int lvl = player.experienceLevel;
        player.teleportTo(world, x, y, z, player.getYRot(), player.getXRot());
        player.setExperienceLevels(lvl);
    }
}
