package me.alex.faygo.listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@RequiredArgsConstructor
public class FreezeListener implements Listener {

    @NonNull
    private FaygoPlugin plugin;

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        Profile playerProfile = this.plugin.getProfileService().find(player.getName());
        Profile damagerProfile = this.plugin.getProfileService().find(damager.getName());

        if(playerProfile.isFrozen() || damagerProfile.isFrozen() ) event.setCancelled(true);

    }

}
