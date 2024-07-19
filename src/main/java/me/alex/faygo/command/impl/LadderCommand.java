package me.alex.faygo.command.impl;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import io.github.thatkawaiisam.utils.MessageUtility;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.ladder.Ladder;
import me.alex.faygo.ladder.LadderService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static io.github.thatkawaiisam.utils.MessageUtility.formatMessage;

@RequiredArgsConstructor
public class LadderCommand {

    @NonNull
    private FaygoPlugin plugin;

    @Command(
            aliases = "new",
            perms = "faygo.admin",
            desc = "creates a new ladder"
    )
    public void newLadder(@Sender Player player, String name) {
        /* check if ladder with the same name already exists */
        Ladder ladder = this.plugin.getLadderService().find(name);

        if(ladder != null) {
            player.sendMessage(ChatColor.RED + "Ladder already exists");
            return;
        }

        /* if it doesn't create the ladder */
        ladder = new Ladder(name);
        ladder.setItem(new ItemStack(Material.ANVIL));
        ladder.setInventory(player.getInventory());

        this.plugin.getLadderService().saveLadder(ladder);
        player.sendMessage(ChatColor.YELLOW + "Created ladder "+ name);
    }

    @Command(
            aliases = "delete",
            perms = "faygo.admin",
            desc = "deletes a ladder"
    )
    public void deleteLadder(@Sender Player player, Ladder ladder) {
        this.plugin.getLadderService().delete(ladder);

        player.sendMessage(ChatColor.YELLOW + "Deleted " + ladder.getName());
    }


    @Command(
            aliases = "list",
            perms = "faygo.admin",
            desc = "lists all ladders"
    )
    public void listLadders(@Sender Player player) {
        player.sendMessage(formatMessage("&7&m--------------------------------"));

        if(this.plugin.getLadderService().getLadders().size() == 0) {
            player.sendMessage(ChatColor.GRAY + "No ladders found");
        }

        this.plugin.getLadderService().getLadders().forEach(current -> {
            player.sendMessage(formatMessage("&7- &f" + current.getName()));
        });

        player.sendMessage(formatMessage("&7&m--------------------------------"));
        return;
    }

    @Command(
            aliases = "setitem",
            perms = "faygo.admin",
            desc = "sets the item of a ladder"
    )
    public void setItem(@Sender Player player, Ladder ladder) {
        ladder.setItem(player.getItemInHand());
        this.plugin.getLadderService().saveLadder(ladder);

        player.sendMessage(ChatColor.YELLOW + "Set the display item of " + ladder.getName());
    }

    @Command(
            aliases = "setinv",
            perms = "faygo.admin",
            desc = "sets a ladder's inventory"
    )
    public void setInv(@Sender Player player, Ladder ladder) {
        ladder.setInventory(player.getInventory());
        ladder.setArmor(player.getInventory().getArmorContents());

        this.plugin.getLadderService().saveLadder(ladder);

        player.sendMessage(ChatColor.YELLOW + "Set " + ladder.getName() + "'s inventory");
    }

    @Command(
            aliases = "getinv",
            perms = "faygo.admin",
            desc = "gets a ladder's inventory"
    )
    public void getInv(@Sender Player player, Ladder ladder) {
        player.getInventory().setContents(ladder.getInventory().getContents());
        player.getInventory().setArmorContents(ladder.getArmor());

        player.sendMessage(ChatColor.YELLOW + "Equipped " + ladder.getName());
    }

}
