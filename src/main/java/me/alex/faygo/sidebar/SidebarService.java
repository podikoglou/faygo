package me.alex.faygo.sidebar;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.service.Service;

@RequiredArgsConstructor
public class SidebarService implements Service {

    @NonNull
    private FaygoPlugin plugin;

    @Getter
    private AssembleAdapter adapter;

    @Getter
    private Assemble assemble;

    /**
     * Open the service
     */
    @Override
    public void open() {
        this.adapter = new SidebarAdapter(this.plugin);
        this.assemble = new Assemble(this.plugin, this.adapter);
    }

    /**
     * Close the service
     */
    @Override
    public void close() {

    }
}
