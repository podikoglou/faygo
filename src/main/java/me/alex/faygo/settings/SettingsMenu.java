package me.alex.faygo.settings;

import io.github.nosequel.katakuna.button.Button;
import io.github.nosequel.katakuna.button.impl.ButtonBuilder;
import io.github.nosequel.katakuna.menu.Menu;
import io.github.thatkawaiisam.utils.MessageUtility;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static io.github.thatkawaiisam.utils.MessageUtility.*;
import static io.github.thatkawaiisam.utils.MessageUtility.formatMessage;

public class SettingsMenu extends Menu {

    private final Profile profile;

    public SettingsMenu(Player player) {
        super(player, "Settings", 3 * 9);
        this.profile = FaygoPlugin.getFaygo().getProfileService().find(player.getName());
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();

        /* private messages */
        buttons.add(
                new ButtonBuilder(Material.BOOK_AND_QUILL)
                        .setDisplayName((profile.isPm() ? ChatColor.GREEN : ChatColor.RED) + "Private Messages")
                        .setLore(formatMessage("&6Click to " + (profile.isPm() ? ChatColor.RED + "disable" : ChatColor.GREEN + "enable") + " &6private messages"))
                        .setAction(p -> {
                            this.getPlayer().chat("/tpm");
                            this.updateMenu();
                        })
                        .setIndex(11)
        );

        /* global chat */
        buttons.add(
                new ButtonBuilder(Material.PAPER)
                        .setDisplayName((profile.isChat() ? ChatColor.GREEN : ChatColor.RED) + "Global Chat")
                        .setLore(formatMessage("&6Click to " + (profile.isChat() ? ChatColor.RED + "disable" : ChatColor.GREEN + "enable") + " Global Chat"))
                        .setAction(p -> {
                            this.getPlayer().chat("/tgc");
                            this.updateMenu();
                        })
                        .setIndex(13)
        );

        /* sidebar */
        buttons.add(
                new ButtonBuilder(Material.SIGN)
                        .setDisplayName((profile.isSidebar() ? ChatColor.GREEN : ChatColor.RED) + "Sidebar")
                        .setLore(formatMessage("&6Click to " + (profile.isSidebar() ? ChatColor.RED + "disable" : ChatColor.GREEN + "enable") + " sidebar"))
                        .setAction(p -> {
                            this.getPlayer().chat("/tsb");
                            this.updateMenu();
                        })
                        .setIndex(15)
        );

        return buttons;
    }
}
