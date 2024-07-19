package me.alex.faygo.item;

import io.github.thatkawaiisam.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static io.github.thatkawaiisam.utils.MessageUtility.formatMessage;

public class ItemConstants {

    /**
     * Unranked Queue item
     */
    public static ItemStack UNRANKED_QUEUE = new ItemBuilder(Material.IRON_SWORD)
            .title(formatMessage("&bUnranked Queue"))
            .lores(Arrays.asList(
                    formatMessage("&7Right click to join an Unranked Queue")
            ))
            .build();

    /**
     * Ranked Queue item
     */
    public static ItemStack RANKED_QUEUE = new ItemBuilder(Material.DIAMOND_SWORD)
            .title(formatMessage("&6Ranked Queue"))
            .lores(Arrays.asList(
                    formatMessage("&7Right click to join a Ranked Queue")
            )).build();

    /**
     * Settings Menu Item
     */
    public static ItemStack SETTINGS_MENU = new ItemBuilder(Material.ITEM_FRAME)
            .title(formatMessage("&eSettings"))
            .lores(Arrays.asList(
                    formatMessage("&7Right click to open the Settings Menu")
            ))
            .build();

    /**
     * Shop Item
     */
    public static ItemStack SHOP_MENU = new ItemBuilder(Material.EMERALD)
            .title(formatMessage("&aShop"))
            .lores(Arrays.asList(
                    formatMessage("&7Right click to open the Shop Menu")
            ))
            .build();

    /**
     * Leave Queue Item
     */
    public static ItemStack LEAVE_QUEUE = new ItemBuilder(Material.BLAZE_POWDER)
            .title(formatMessage("&cLeave Queue"))
            .lores(Arrays.asList(
                    formatMessage("&7Right click to leave your queue")
            ))
            .build();

    /**
     * Kit Map Item
     */
    public static ItemStack KITMAP = new ItemBuilder(Material.getMaterial(351))
            .durability((short) 14)
            .title(formatMessage("&bKit Map"))
            .lores(Arrays.asList(
                    formatMessage("&7Right click to warp to the Kit Map")
            ))
            .build();

    /**
     * Load Kit Item
     */
    public static ItemStack LOAD_KIT = new ItemBuilder(Material.ENCHANTED_BOOK)
            .title(formatMessage("&dLoad Kit #1"))
            .lores(Arrays.asList(
                    formatMessage("&7Right click to receive the Kit #1")
            ))
            .build();

    /**
     * Staff Mode View Inventory
     */
    public static ItemStack STAFF_MODE_VIEW_INVENTORY = new ItemBuilder(Material.BOOK)
            .title("&eView Inventory")
            .lores("&7Right click on a player to view their inventory")
            .build();

    /**
     * Staff Mode Freeze
     */
    public static ItemStack STAFF_MODE_FREEZE = new ItemBuilder(Material.IRON_FENCE)
            .title("&eFreeze Player")
            .lores("&7Right click on a player to freeze them")
            .build();

    /**
     * Arena Edit Set Location 1
     */
    public static ItemStack SET_LOCATION_1 = new ItemBuilder(Material.GOLD_BLOCK)
            .title("&eSet Location 1")
            .build();

    /**
     * Arena Edit Set Location 2
     */
    public static ItemStack SET_LOCATION_2 = new ItemBuilder(Material.GOLD_BLOCK)
            .title("&eSet Location 2")
            .build();

}
