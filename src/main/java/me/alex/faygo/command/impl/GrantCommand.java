package me.alex.faygo.command.impl;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.data.DataPlugin;
import me.alex.data.data.DataService;
import me.alex.data.rank.Rank;
import me.alex.data.user.User;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GrantCommand {

    @NonNull
    private FaygoPlugin plugin;

    private DataService service = DataPlugin.getDataPlugin().getDataService();

    @Command(
            aliases = {"grant"},
            perms = "command.grant",
            desc = "grants someone a rank",
            usage = "<player> <rank>"
    )
    public void grant(@Sender Player player, User target, Rank rank) {
        // start timer
        long started = System.currentTimeMillis();

        // update the user's rank
        target.setRank(rank);
        this.service.saveUser(target);

        // end timer
        long ended = System.currentTimeMillis();
        long time = ended - started;

        player.sendMessage(ChatColor.GREEN + "Action completed in " + time + "ms");

        // let the player know (if they're online)
        try {
            String message = ChatColor.GOLD + "You have been granted the " + rank.getColor() + rank.toString() + ChatColor.GOLD + " rank.";
            Bukkit.getPlayer(target.getName()).sendMessage(message);

            Profile profile = this.plugin.getProfileService().find(target.getName());
            profile.refreshNametag();
        } catch (NullPointerException exception) {
            // don't handle it
        }

    }

}
