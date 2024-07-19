package me.alex.faygo.listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class StaffChatListener implements Listener {

    @NonNull
    private FaygoPlugin plugin;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if(profile.isStaffChat()) {
            event.setCancelled(true);

            String message = ChatColor.DARK_RED + "[Staff] " + ChatColor.WHITE + player.getName()  + ": " + event.getMessage();
            Bukkit.getOnlinePlayers().stream()
                    .filter(current -> current.isOp() || current.hasPermission("faygo.staff"))
                    .forEach(current -> current.sendMessage(message));
        }
    }


}
