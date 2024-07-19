package me.alex.faygo.queue;

import io.github.nosequel.katakuna.button.Button;
import io.github.nosequel.katakuna.button.impl.ButtonBuilder;
import io.github.nosequel.katakuna.menu.Menu;
import io.github.thatkawaiisam.utils.MessageUtility;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.ladder.Ladder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static io.github.thatkawaiisam.utils.MessageUtility.*;

public class QueueMenu extends Menu {

    private final Player player;
    private final QueueType type;

    public QueueMenu(Player player, QueueType type) {
        super(player, "Queue " + capitalizeFirstLetter(type.name().toLowerCase()), 9);
        this.player = player;
        this.type = type;
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();

        FaygoPlugin.getFaygo().getQueueService().getQueues().stream()
                .filter(queue -> queue.getType().equals(this.type))
                .forEach(queue -> {

                    Ladder ladder = queue.getLadder();
                    ItemStack item = ladder.getItem();

                    buttons.add(
                            new ButtonBuilder(item)
                                .setDisplayName(ChatColor.DARK_RED + ladder.getName())
                                .setLore(
                                        formatMessage("&7&m---------------------------------"),
                                        formatMessage("&7Click to queue " + ChatColor.DARK_RED + capitalizeFirstLetter(this.type.name().toLowerCase()) + " " + ladder.getName()),
                                        formatMessage(""),
                                        formatMessage("&7In Queue: &f" + queue.getPlayers().size()),
                                        formatMessage("&7Fighting: &f0"), // TODO: Complete this
                                        formatMessage("&7&m---------------------------------")
                                )
                                .setAction($ -> {
                                    FaygoPlugin.getFaygo().getQueueService().add(this.player, queue);
                                    player.closeInventory();
                                })
                    );

                });

        return buttons;
    }
}
