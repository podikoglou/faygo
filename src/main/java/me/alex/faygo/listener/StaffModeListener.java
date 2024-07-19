package me.alex.faygo.listener;

import io.github.thatkawaiisam.utils.MessageUtility;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.item.ItemConstants;
import me.alex.faygo.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static io.github.thatkawaiisam.utils.MessageUtility.formatMessage;
import static me.alex.faygo.item.ItemConstants.*;

@RequiredArgsConstructor
public class StaffModeListener implements Listener {

    @NonNull
    private FaygoPlugin plugin;

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        /* if the player is not holding an item, return */
        if(player.getItemInHand() == null) return;

        /* get the item */
        ItemStack item = player.getItemInHand();

        /* if the item is not similar to the freeze or view inventory item, return */
        if(!(item.isSimilar(STAFF_MODE_FREEZE) || item.isSimilar(STAFF_MODE_VIEW_INVENTORY))) return;

        /* get the clicked player and the player's profile */
        Player clicked = (Player) event.getRightClicked();
        Profile profile = this.plugin.getProfileService().find(player.getName());

        /* if the player is in staff mode */
        if(profile.isStaffMode()) {

            /* if player is holding the view inventory item */
            if(item.isSimilar(STAFF_MODE_VIEW_INVENTORY)) {
                player.openInventory(clicked.getInventory());
                return;
            }

            /* if player is holding the freeze item */
            if(item.isSimilar(STAFF_MODE_FREEZE)) {

                /* get the clicked player's profile */
                Profile clickedProfile = this.plugin.getProfileService().find(player.getName());

                player.chat("/freeze " + clicked.getName());

                return;
            }

        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if(profile.isStaffMode()) event.setCancelled(true);
    }

}
