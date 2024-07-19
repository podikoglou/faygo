package me.alex.faygo;

import io.github.nosequel.katakuna.MenuHandler;
import lombok.Getter;
import me.alex.faygo.arena.Arena;
import me.alex.faygo.arena.ArenaService;
import me.alex.faygo.command.CommandService;
import me.alex.faygo.item.ItemService;
import me.alex.faygo.ladder.LadderService;
import me.alex.faygo.listener.*;
import me.alex.faygo.profile.ProfileService;
import me.alex.faygo.queue.QueueService;
import me.alex.faygo.sidebar.SidebarService;
import me.alex.faygo.utility.EntityHider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FaygoPlugin extends JavaPlugin {

    @Getter
    private static FaygoPlugin faygo;

    @Getter
    private CommandService commandService;

    @Getter
    private SidebarService sidebarService;

    @Getter
    private ProfileService profileService;

    @Getter
    private ItemService itemService;

    @Getter
    private ArenaService arenaService;

    @Getter
    private LadderService ladderService;

    @Getter
    private QueueService queueService;

    @Getter
    private EntityHider entityHider;

    @Getter
    private MenuHandler menuHandler;

    @Override
    public void onEnable() {
        faygo = this;

        /* profile service */
        this.profileService = new ProfileService();
        this.profileService.open();

        /* sidebar service */
        this.sidebarService = new SidebarService(this);
        this.sidebarService.open();

        /* item service */
        this.itemService = new ItemService(this);
        this.itemService.open();

        /* arena service */
        this.arenaService = new ArenaService(this);
        this.arenaService.open();

        /* ladder service */
        this.ladderService = new LadderService(this);
        this.ladderService.open();

        /* command service */
        this.commandService = new CommandService(this, new FaygoModule(this));
        this.commandService.open();

        /* queue service */
        this.queueService = new QueueService(this);
        this.queueService.open();

        /* entity hider */
        this.entityHider = new EntityHider(this, EntityHider.Policy.BLACKLIST);

        /* initialize menu handler */
        this.menuHandler = new MenuHandler(this);

        /* register listeners */
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GeneralListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new InteractionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new StaffModeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new FreezeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new StaffChatListener(this), this);
    }

    @Override
    public void onDisable() {
        /* command service */
        this.commandService.close();

        /* profile service */
        this.profileService.close();

        /* sidebar service */
        this.sidebarService.close();

        /* item service */
        this.itemService.close();

        /* arena service */
        this.arenaService.close();

        /* ladder service */
        this.ladderService.close();

        /* queue service */
        this.queueService.close();
    }

}
