package me.alex.faygo.item;

import io.github.thatkawaiisam.utils.PlayerUtility;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import me.alex.faygo.profile.state.ProfileState;
import me.alex.faygo.service.Service;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import static me.alex.faygo.item.ItemConstants.*;

@RequiredArgsConstructor
public class ItemService implements Service {

    @NonNull
    private FaygoPlugin plugin;

    /**
     * Open the service
     */
    @Override
    public void open() {

    }

    /**
     * Close the service
     */
    @Override
    public void close() {

    }

    /**
     * Gives the appropreite items to a player
     *
     * @param profile the profile
     */
    public void giveItems(Profile profile) {
        Player player = profile.toPlayer();
        PlayerInventory inventory = player.getInventory();

        PlayerUtility.clearInventory(player);

        if(profile.isStaffMode()) {
            inventory.setItem(0, STAFF_MODE_VIEW_INVENTORY);
            inventory.setItem(1, STAFF_MODE_FREEZE);

            player.updateInventory();

            return;
        }

        if(profile.getState() == ProfileState.LOBBY) {
            inventory.setItem(0, UNRANKED_QUEUE);
            inventory.setItem(1, RANKED_QUEUE);
            inventory.setItem(2, KITMAP);
            inventory.setItem(7, SHOP_MENU);
            inventory.setItem(8, SETTINGS_MENU);

            player.updateInventory();

            return;
        }

        if(profile.getState() == ProfileState.EDITING_ARENA) {
            inventory.setItem(0, SET_LOCATION_1);
            inventory.setItem(1, SET_LOCATION_2);

            player.updateInventory();

            return;
        }

        if(profile.getState() == ProfileState.QUEUE) {
            inventory.setItem(8, LEAVE_QUEUE);

            player.updateInventory();

            return;
        }

    }
}
