package me.alex.faygo.command.impl;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import app.ashcon.intake.parametric.annotation.Default;
import app.ashcon.intake.parametric.annotation.Text;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static io.github.thatkawaiisam.utils.MessageUtility.formatMessage;
import static me.alex.faygo.profile.state.ProfileState.*;
import static me.alex.faygo.profile.state.ProfileState.EDITING_ARENA;

@RequiredArgsConstructor
public class StaffCommands {

    @NonNull
    private FaygoPlugin plugin;

    @Command(
            aliases = {"h", "mod", "staff", "sm", "staffmode"},
            perms = "faygo.staff",
            desc = "toggles staff mode"
    )
    public void staffMode(@Sender Player player) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if(profile.getState() == QUEUE || profile.getState() == PLAYING || profile.getState() == EDITING_ARENA) {
            player.sendMessage(ChatColor.RED + "You can't do that at your current state");
            return;
        }

        profile.setStaffMode(!profile.isStaffMode());
        player.sendMessage(ChatColor.YELLOW + "Staff Mode has been " + (profile.isStaffMode() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));

        if(!profile.isStaffMode()) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }

        player.setGameMode(profile.isStaffMode() ? GameMode.CREATIVE : GameMode.SURVIVAL);
        this.plugin.getItemService().giveItems(profile);
    }

    @Command(
            aliases = {"sc", "staffchat"},
            perms = "faygo.staff",
            desc = "toggles staff chat"
    )
    public void staffChat(@Sender Player player, @Text @Default("$toggle") String message) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        if(message.equals("$toggle")) {
            profile.setStaffChat(!profile.isStaffChat());
            player.sendMessage(ChatColor.YELLOW + "Staff Chat has been " + (profile.isStaffChat() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
        } else {
            String text = ChatColor.DARK_RED + "[Staff] " + ChatColor.WHITE +  player.getName() + ": " + message;
            Bukkit.getOnlinePlayers().stream()
                    .filter(current -> current.isOp() || current.hasPermission("faygo.staff"))
                    .forEach(current -> current.sendMessage(text));
        }
    }

    @Command(
            aliases = {"freeze", "ss", "screenshare"},
            perms = "faygo.freeze",
            desc = "freezes someone"
    )
    public void freeze(@Sender Player player, Player target) {
        Profile targetProfile = this.plugin.getProfileService().find(target.getName());

        /* if the clicked player is frozen */
        if(targetProfile.isFrozen()) {

            /* unfreeze the player */
            targetProfile.setFrozen(false);
            player.sendMessage(ChatColor.YELLOW + "You have unfrozen " + targetProfile.getName());

            target.sendMessage(ChatColor.YELLOW + "You have been unfrozen.");

            target.removePotionEffect(PotionEffectType.BLINDNESS);
            target.removePotionEffect(PotionEffectType.SPEED);
            target.removePotionEffect(PotionEffectType.JUMP);

            /* if the clicked player is not frozen */
        } else {
            /* freeze the player */
            targetProfile.setFrozen(true);

            player.sendMessage(ChatColor.YELLOW + "You have frozen " + targetProfile.getName());

            target.sendMessage(formatMessage("&7&m-------------------------------------------------"));
            target.sendMessage(formatMessage("&4&lYou have been frozen!"));
            target.sendMessage(formatMessage(""));
            target.sendMessage(formatMessage("&4Join our discord at &fhttps://discord.3OO.club"));
            target.sendMessage(formatMessage("&4to be screenshared"));
            target.sendMessage(formatMessage(""));
            target.sendMessage(formatMessage("&4&lWARNING: &fIf you leave, you'll be banned."));
            target.sendMessage(formatMessage("&7&m-------------------------------------------------"));

            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 999999));
            target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, -999));
            target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 200));
        }

    }

}
