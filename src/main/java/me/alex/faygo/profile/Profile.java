package me.alex.faygo.profile;

import lombok.Getter;
import lombok.Setter;
import me.alex.data.user.User;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.arena.Arena;
import me.alex.faygo.profile.state.ProfileState;
import me.alex.faygo.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Profile {

    private final User user;
    private final String name;
    private ProfileState state;

    /* settings */
    public boolean chat, sidebar, pm, build, staffMode, staffChat;

    private boolean frozen;
    private Arena arenaEditing;

    /**
     * Creates the profile
     *
     * @param user the user
     */
    public Profile(User user) {
        this.name = user.getName();
        this.user = user;
        this.state = ProfileState.LOBBY;

        /* settings */
        this.chat = true;
        this.staffMode = false;
        this.sidebar = true;
        this.pm = true;
    }

    /**
     * Gets the player of the profile
     *
     * @return the player
     */
    public Player toPlayer() {
        return Bukkit.getPlayer(user.getName());
    }

    /**
     * Finds the queue the player is in
     *
     * @return the queue
     */
    public Queue getQueue() {
        List<Queue> queues = FaygoPlugin.getFaygo().getQueueService().getQueues().stream()
                .filter(queue -> queue.getPlayers().contains(this))
                .collect(Collectors.toList());

        /* make sure there one or less queues were found */
        assert queues.size() < 2;

        if(queues.size() == 0) return null;

        return queues.get(0);
    }

    /**
     * Refreshes nametag
     */
    public void refreshNametag() {
        this.toPlayer().setPlayerListName(user.getRank().getColor() + this.toPlayer().getName());
    }

}
