package me.alex.faygo.command;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoModule;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.command.impl.*;
import me.alex.faygo.service.Service;

@RequiredArgsConstructor
public class CommandService implements Service {

    @NonNull
    private final FaygoPlugin plugin;

    @NonNull
    private final FaygoModule module;

    /**
     * Open the service
     */
    @Override
    public void open() {
        BasicBukkitCommandGraph graph = new BasicBukkitCommandGraph(this.module);

        graph.getRootDispatcherNode().registerCommands(new GrantCommand(this.plugin));
        graph.getRootDispatcherNode().registerCommands(new ListCommand());
        graph.getRootDispatcherNode().registerCommands(new SettingsCommand(this.plugin));
        graph.getRootDispatcherNode().registerCommands(new StaffCommands(this.plugin));
        graph.getRootDispatcherNode().registerCommands(new PingCommand());

        graph.getRootDispatcherNode().registerNode("arena").registerCommands(new ArenaCommand(this.plugin));
        graph.getRootDispatcherNode().registerNode("ladder").registerCommands(new LadderCommand(this.plugin));

        new BukkitIntake(this.plugin, graph).register();
    }

    /**
     * Close the service
     */
    @Override
    public void close() {

    }

}
