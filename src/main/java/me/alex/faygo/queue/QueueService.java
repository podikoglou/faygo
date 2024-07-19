package me.alex.faygo.queue;

import io.github.thatkawaiisam.utils.MessageUtility;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import me.alex.faygo.profile.state.ProfileState;
import me.alex.faygo.service.Service;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.stream.Stream;

import static io.github.thatkawaiisam.utils.MessageUtility.capitalizeFirstLetter;

@RequiredArgsConstructor
@Getter
public class QueueService implements Service {

    @NonNull
    private FaygoPlugin plugin;

    private HashSet<Queue> queues;

    /**
     * Open the service
     */
    @Override
    public void open() {
        this.queues = new HashSet<>();

        this.reloadQueues();
    }

    /**
     * Adds a player to a queue
     *
     * @param player the player
     * @param queue the queue
     */
    public void add(Player player, Queue queue) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        assert profile != null;

        profile.setState(ProfileState.QUEUE);
        this.plugin.getItemService().giveItems(profile);

        /* make sure the player is not already in the queue */
        assert !queue.getPlayers().contains(profile);

        /* add the player to the queue */
        queue.getPlayers().add(profile);

        player.sendMessage(ChatColor.YELLOW + "You have been added in the " + capitalizeFirstLetter(queue.getType().name().toLowerCase()) + " " + queue.getLadder().getName());

        /* check if the queue is full */
        if(queue.getPlayers().size() >= 2) {
            Profile profile2 = queue.getPlayers().stream()
                    .filter(current -> !current.equals(profile))
                    .findFirst()
                    .orElse(null);

            assert profile2 != null;

            Stream.of(profile, profile2).forEach(current -> {
                this.remove(current.toPlayer(), queue, ProfileState.PLAYING);

                current.toPlayer().sendMessage(ChatColor.YELLOW + "Found opponent!");
                current.toPlayer().sendMessage(ChatColor.YELLOW + "Please wait, starting match");
            });
        }


    }

    /**
     * Removes a player form the queue
     *
     * @param player the player
     * @param queue the queue
     */
    public void remove(Player player, Queue queue, ProfileState newState) {
        Profile profile = this.plugin.getProfileService().find(player.getName());

        profile.setState(newState);
        this.plugin.getItemService().giveItems(profile);

        assert profile != null;
        assert queue.getPlayers().contains(profile);

        queue.getPlayers().remove(profile);

        player.sendMessage(ChatColor.RED + "You have been removed from the queue");
    }

    /**
     * Reloads queues
     */
    public void reloadQueues() {
        this.queues.clear();

        this.plugin.getLadderService().getLadders().forEach(ladder -> {

            Stream.of(QueueType.values()).forEach(type -> this.queues.add(new Queue(ladder, type)));

        });
    }

    /**
     * Close the service
     */
    @Override
    public void close() {

    }

}
