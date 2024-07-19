package me.alex.faygo.listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static io.github.thatkawaiisam.utils.MessageUtility.formatMessage;

@RequiredArgsConstructor
public class GeneralListeners implements Listener {

    @NonNull
    private FaygoPlugin plugin;

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemFramePlace(PlayerInteractEvent event) {
        if(event.hasItem() && event.getItem().getType() == Material.ITEM_FRAME && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd(formatMessage("&4&l3Hunned &7- &fDevelopment\n&fJoin us at &7&ohttps://discord.3OO.club"));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Profile profile = this.plugin.getProfileService().find(event.getPlayer().getName());

        if(!profile.isChat()) {
            event.setCancelled(true);
        }

        event.getRecipients().stream()
                .filter(player -> {
                    Profile current = this.plugin.getProfileService().find(player.getName());

                    return !current.isChat();
                })
                .forEach(player -> event.getRecipients().remove(player));
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
