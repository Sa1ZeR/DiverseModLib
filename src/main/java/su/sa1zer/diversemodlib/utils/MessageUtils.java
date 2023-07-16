package su.sa1zer.diversemodlib.utils;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
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

    public static void sendChatMessage(CommandSourceStack sender, String message, String... args) {
        sender.sendSystemMessage(Component.literal(String.format(message, args)));
    }

    public static void sendChatMessage(CommandSourceStack sender, String message) {
        sender.sendSystemMessage(Component.literal(message));
    }

    public static void sendBroadcastMessage(String message, String... args) {
        PlayerList playerList = ServerUtils.getServer().getPlayerList();

        for(ServerPlayer player : playerList.getPlayers()) {
            sendChatMessage(player, message, args);
        }
    }

    public static void sendBroadcastClientMessage(String message, String... args) {
        PlayerList playerList = ServerUtils.getServer().getPlayerList();

        for(ServerPlayer player : playerList.getPlayers()) {
            sendClientMessage(player, message, args);
        }
    }
}
