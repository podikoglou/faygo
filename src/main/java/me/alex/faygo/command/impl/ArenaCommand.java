package me.alex.faygo.command.impl;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.arena.Arena;

import me.alex.faygo.profile.Profile;
import me.alex.faygo.profile.state.ProfileState;
import me.alex.faygo.utility.Loc;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static io.github.thatkawaiisam.utils.MessageUtility.*;

@RequiredArgsConstructor
public class ArenaCommand {

    @NonNull
    private FaygoPlugin plugin;

    @Command(
            aliases = "new",
            perms = "faygo.admin",
            desc = "creates a new arena"
    )
    public void newArena(@Sender Player player, String name) {
        /* find arena */
        Arena arena = this.plugin.getArenaService().find(name);

        /* check if arena exists */
        if(arena != null) {
            player.sendMessage(ChatColor.RED + "This arena already exists");
            return;
        }

        /* create arena */
        arena = new Arena(name);
        arena.setLoc1(Loc.fromLocation(player.getLocation()));
        arena.setLoc2(Loc.fromLocation(player.getLocation()));

        /* save arena */
        this.plugin.getArenaService().saveArena(arena);
        player.sendMessage(ChatColor.YELLOW + "Created arena " + arena.getName());

        return;
    }

    @Command(
            aliases = "delete",
            perms = "faygo.admin",
            desc = "deletes an arena"
    )
    public void deleteArena(@Sender Player player, Arena arena) {
        this.plugin.getArenaService().deleteArena(arena);
        player.sendMessage(ChatColor.YELLOW + "Deleted arena " + arena.getName());

        return;
    }

    @Command(
            aliases = "list",
            perms = "faygo.admin",
            desc = "lists arenas"
    )
    public void listArenas(@Sender Player player) {
        player.sendMessage(formatMessage("&7&m--------------------------------"));

        if(this.plugin.getArenaService().getArenas().size() == 0) {
            player.sendMessage(ChatColor.GRAY + "No arenas found");
        }

        this.plugin.getArenaService().getArenas().forEach(current -> {
            player.sendMessage(formatMessage("&7- &f" + current.getName()));
        });

        player.sendMessage(formatMessage("&7&m--------------------------------"));
        return;

    }

    @Command(
            aliases = "edit",
            perms = "faygo.admin",
            desc = "edits an arena"
    )
    public void editArena(@Sender Player player, Arena arena) {
        if(arena == null) return;

        Profile profile = this.plugin.getProfileService().find(player.getName());

        profile.setState(ProfileState.EDITING_ARENA);
        profile.setArenaEditing(arena);
        this.plugin.getItemService().giveItems(profile);

        player.sendMessage(ChatColor.YELLOW + "Editing arena " + arena.getName());
        player.sendMessage(ChatColor.YELLOW + "Reconnect when you're done!");

        return;

    }

}
