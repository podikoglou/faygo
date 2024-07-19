package me.alex.faygo.sidebar;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import io.github.thatkawaiisam.utils.MessageUtility;
import io.github.thatkawaiisam.utils.PingUtility;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.data.user.User;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.profile.Profile;
import me.alex.faygo.profile.state.ProfileState;
import me.alex.faygo.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static io.github.thatkawaiisam.utils.MessageUtility.capitalizeFirstLetter;
import static me.alex.faygo.profile.state.ProfileState.*;

@RequiredArgsConstructor
public class SidebarAdapter implements AssembleAdapter {

    @NonNull
    private FaygoPlugin plugin;

    @Override
    public String getTitle(Player player) {
        return "&4&l3Hunned";
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();

        Profile profile = this.plugin.getProfileService().find(player.getName());
        User user = profile.getUser();

        if(!profile.isSidebar()) return toReturn;

        toReturn.add("&7&m-----------------------");

        if(profile.getState() == LOBBY) {
            toReturn.add("&4Online: &f" + Bukkit.getOnlinePlayers().size());
            toReturn.add("&4Credits: &f"  + user.getCredits());
            toReturn.add("&4Ping: &f"  + PingUtility.getPing(player));
        }

        if(profile.getState() == QUEUE) {
            Queue queue = profile.getQueue();

            toReturn.add("&7In queue");
            toReturn.add("");
            toReturn.add(ChatColor.DARK_RED + capitalizeFirstLetter(queue.getType().name().toLowerCase()) + " " + queue.getLadder().getName());
        }

        toReturn.add("&7&m-----------------------");

        return toReturn;
    }

}
