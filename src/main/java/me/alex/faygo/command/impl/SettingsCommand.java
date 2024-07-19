package me.alex.faygo.command.impl;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import me.alex.faygo.settings.SettingsMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SettingsCommand {

    @NonNull
    private FaygoPlugin plugin;

    @Command(
            aliases = { "settings", "options" },
            desc = "opens settings menu"
    )
    public void settings(@Sender Player player) {
        new SettingsMenu(player).updateMenu();
    }

    @Command(
            aliases = { "toggleprivatemessages", "tpm" },
            desc = "toggles private messages"
    )
    public void togglePrivateMessages(@Sender Player player) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        /* toggle the setting */
        profile.pm = !profile.pm;

        /* create boolean format */
        String status = profile.pm ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";

        /* announce it to the player */
        player.sendMessage(ChatColor.YELLOW + "Private messages are now " + status);
    }

    @Command(
            aliases = { "tgc", "togglechat", "toggleglobalchat" },
            desc = "toggles global chat"
    )
    public void toggleGlobalChat(@Sender Player player) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        /* toggle the setting */
        profile.chat = !profile.chat;

        /* create boolean format */
        String status = profile.chat ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";

        /* announce it to the player */
        player.sendMessage(ChatColor.YELLOW + "Global chat is now " + status);
    }

    @Command(
            aliases = { "togglesidebar", "togglescoreboard", "tsb" },
            desc = "toggles sidebar"
    )
    public void toggleSidebar(@Sender Player player) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        /* toggle the setting */
        profile.sidebar = !profile.sidebar;

        /* create boolean format */
        String status = profile.sidebar ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";

        /* announce it to the player */
        player.sendMessage(ChatColor.YELLOW + "Sidebar is now " + status);
    }

    @Command(
            aliases = { "togglebuildmode", "togglebuild", "build" },
            desc = "toggles build mode",
            perms = "faygo.build"
    )
    public void toggleBuild(@Sender Player player) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        /* toggle the setting */
        profile.build = !profile.build;

        /* create boolean format */
        String status = profile.build ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";

        /* announce it to the player */
        player.sendMessage(ChatColor.YELLOW + "Build mode is now " + status);
    }



}
