package su.sa1zer.diversemodlib.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class MessageUtils {

    public static void sendClientMessage(Player player, String message) {
        player.displayClientMessage(Component.literal(message), true);
    }

    public static void sendClientMessage(Player player, String message, String... args) {
        player.displayClientMessage(Component.literal(String.format(message, args)), true);
    }

    public static void sendChatMessage(Player player, String message) {
        player.sendSystemMessage(Component.literal(message));
    }

    public static void sendChatMessage(Player player, String message, String... args) {
        player.sendSystemMessage(Component.literal(String.format(message, args)));
    }


}
