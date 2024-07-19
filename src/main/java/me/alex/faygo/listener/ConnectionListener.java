package me.alex.faygo.listener;

import io.github.thatkawaiisam.utils.PlayerUtility;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.data.DataPlugin;
import me.alex.data.user.User;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import net.sf.cglib.asm.$TypePath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import static io.github.thatkawaiisam.utils.MessageUtility.formatMessage;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {

    @NonNull
    private FaygoPlugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = DataPlugin.getDataPlugin().getDataService().getUser(player.getName());
        Profile profile = this.plugin.getProfileService().find(player.getName());

        event.setJoinMessage("");

        /* reset player */
        PlayerUtility.clearInventory(player);
        PlayerUtility.feedPlayer(player);
        PlayerUtility.healPlayer(player);

        /* teleport to spawn */
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());

        /* change nametag */
        profile.refreshNametag();

        /* send join message */
        player.sendMessage(ChatColor.DARK_RED + "Welcome to " + ChatColor.BOLD + "3Hunned");
        player.sendMessage(ChatColor.DARK_RED + "Join our discord at " + ChatColor.WHITE + "https://discord.3OO.club/");
        player.sendMessage(ChatColor.DARK_RED + "Visit our website at " + ChatColor.WHITE + "https://3OO.club/");

        /* give items */
        this.plugin.getItemService().giveItems(profile);

        /* reset permissions */
        player.setOp(false);
        player.getEffectivePermissions().stream()
                .filter(info -> info.getAttachment() != null)
                .forEach(info -> player.removeAttachment(info.getAttachment()));
        player.getEffectivePermissions().clear();
        player.recalculatePermissions();

        /* apply permissions */
        if(user.getRank().getPermissions().contains("*")) {

            player.setOp(true);

        } else {

            PermissionAttachment permissionAttachment = player.addAttachment(this.plugin);

            user.getRank().getPermissions().forEach(permission -> {
                permissionAttachment.setPermission(permission, true);
            });

            player.recalculatePermissions();

        }

        /* notify staff members */
        if(player.isOp() || player.hasPermission("faygo.staff")) {
            Bukkit.getOnlinePlayers().stream()
                    .filter(current -> current.isOp() || current.hasPermission("faygo.staff"))
                    .forEach(current -> current.sendMessage(ChatColor.DARK_RED + "[Staff] " + ChatColor.YELLOW + player.getName() + " joined"));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("");

        if(player.isOp() || player.hasPermission("faygo.staff")) {
            Bukkit.getOnlinePlayers().stream()
                    .filter(current -> current.isOp() || current.hasPermission("faygo.staff"))
                    .forEach(current -> current.sendMessage(ChatColor.DARK_RED + "[Staff] " + ChatColor.YELLOW + player.getName() + " left"));
        }

        Profile profile = this.plugin.getProfileService().find(player.getName());

        this.plugin.getProfileService().getProfiles().remove(profile);

        if(profile.getQueue() != null) {
            profile.getQueue().getPlayers().remove(player);
        }
    }


}
