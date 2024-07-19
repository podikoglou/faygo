package me.alex.faygo.listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.item.ItemConstants;
import me.alex.faygo.profile.Profile;
import me.alex.faygo.queue.Queue;
import me.alex.faygo.queue.QueueMenu;
import me.alex.faygo.queue.QueueType;
import me.alex.faygo.settings.SettingsMenu;
import me.alex.faygo.utility.Loc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.alex.faygo.item.ItemConstants.*;
import static me.alex.faygo.profile.state.ProfileState.*;

@RequiredArgsConstructor
public class InteractionListener implements Listener {

    @NonNull
    private FaygoPlugin plugin;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (!event.hasItem()) {
            return;
        }

        Player player = event.getPlayer();
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if (event.getItem().isSimilar(SETTINGS_MENU)) {
            new SettingsMenu(player).updateMenu();
            return;
        }

        if(profile.getState() == LOBBY) {

            if(event.getItem().isSimilar(UNRANKED_QUEUE)) {
                new QueueMenu(player, QueueType.UNRANKED).updateMenu();
            }

        }

        if(profile.getState() == QUEUE) {

            if(event.getItem().isSimilar(LEAVE_QUEUE)) {

                assert profile.getQueue() != null;

                Queue queue = profile.getQueue();
                this.plugin.getQueueService().remove(player, queue, LOBBY);

            }

        }

        if (profile.getState() == EDITING_ARENA) {

            if (event.getItem().isSimilar(SET_LOCATION_1)) {
                profile.getArenaEditing().setLoc1(Loc.fromLocation(player.getLocation()));
                player.sendMessage(ChatColor.YELLOW + "Set location 1 of arena " + profile.getArenaEditing().getName());
            }

            if (event.getItem().isSimilar(SET_LOCATION_2)) {
                profile.getArenaEditing().setLoc2(Loc.fromLocation(player.getLocation()));
                player.sendMessage(ChatColor.YELLOW + "Set location 2 of arena " + profile.getArenaEditing().getName());
            }

            this.plugin.getArenaService().saveArena(profile.getArenaEditing());

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(this.plugin.getProfileService().find(event.getPlayer().getName()).build) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(this.plugin.getProfileService().find(event.getPlayer().getName()).build) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        // NOTE: It is assumed that no mobs / animals exist in the server
        Player player = (Player) event.getEntity();
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if(profile.getState() == LOBBY || profile.getState() == QUEUE || profile.getState() == SPECTATING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        // NOTE: It is assumed that both of the entities are playres
        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        Profile playerProfile = this.plugin.getProfileService().find(player.getName());
        Profile damagerProfile = this.plugin.getProfileService().find(damager.getName());

        if(playerProfile.getState() == LOBBY || playerProfile.getState() == QUEUE || playerProfile.getState() == SPECTATING
                || damagerProfile.getState() == LOBBY || damagerProfile.getState() == QUEUE || damagerProfile.getState() == SPECTATING
        ) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDropEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if(profile.getState() == LOBBY || profile.getState() == QUEUE || profile.getState() == SPECTATING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        // TODO: Possibly convert this to a task
        if(event.getTo().getY() <= 190) {
            Player player = event.getPlayer();
            Profile profile = this.plugin.getProfileService().find(player.getName());

            if(profile.getState() == LOBBY || profile.getState() == QUEUE) {
                player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if(profile.build) return;

        if(
                profile.getState() == LOBBY ||
                        profile.getState() == QUEUE ||
                        profile.getState() == SPECTATING ||
                        profile.getState() == EDITING_ARENA
        ) {
            event.setCancelled(true);
        }
    }

}
