package me.alex.faygo.command.impl;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.Type;
import app.ashcon.intake.bukkit.parametric.annotation.Fallback;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import io.github.thatkawaiisam.utils.PingUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PingCommand {

    @Command(
            aliases = "ping",
            desc = "shows your ping"
    )
    public void pin(@Sender Player player, @Fallback(Type.SELF) Player target) {
        player.sendMessage(ChatColor.YELLOW + target.getName() + " has " + PingUtility.getPing(target) + "ms");
    }

}
