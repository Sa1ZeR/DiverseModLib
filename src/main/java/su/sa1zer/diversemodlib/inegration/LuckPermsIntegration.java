package su.sa1zer.diversemodlib.inegration;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;
import java.util.stream.Collectors;

public class LuckPermsIntegration {

    public static boolean hasPermissions(CommandSourceStack stack, String permission) {
        return hasPermissions(stack.getPlayer(), permission);
    }

    public static boolean hasPermissions(ServerPlayer player, String permission) {
        if (player == null)
            return true;

        User user = LuckPermsProvider.get().getPlayerAdapter(ServerPlayer.class).getUser(player);

        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    public static boolean hasGroup(ServerPlayer player, String group) {
        if (player == null)
            return false;

        User user = LuckPermsProvider.get().getPlayerAdapter(ServerPlayer.class).getUser(player);

        Set<String> groups = user.getNodes(NodeType.INHERITANCE).stream()
                .map(InheritanceNode::getGroupName)
                .collect(Collectors.toSet());
        return groups.contains(group);
    }

    public static String getPrefix(ServerPlayer player) {
        if (player == null)
            return "";

        String prefix = LuckPermsProvider.get().getPlayerAdapter(ServerPlayer.class).getUser(player)
                .getCachedData().getMetaData().getPrefix();
        return prefix == null ? "" : prefix;
    }

    public static String getSuffix(ServerPlayer player) {
        if (player == null)
            return "";

        String suffix = LuckPermsProvider.get().getPlayerAdapter(ServerPlayer.class).getUser(player)
                .getCachedData().getMetaData().getSuffix();
        return suffix == null ? "" : suffix;
    }

    public static String getMeta(ServerPlayer player, String key) {
        if (player == null)
            return null;

        return LuckPermsProvider.get().getPlayerAdapter(ServerPlayer.class).getUser(player)
                .getCachedData().getMetaData().getMetaValue(key);
    }
}
