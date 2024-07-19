package me.alex.faygo.arena;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.data.DataPlugin;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.service.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class ArenaService implements Service {

    @NonNull
    private FaygoPlugin plugin;

    private Set<Arena> arenas;
    private Gson gson;
    private Jedis jedis;

    /**
     * Open the service
     */
    @Override
    public void open() {
        this.gson = new Gson();
        this.jedis = DataPlugin.getDataPlugin().getDataService().getJedis();
        this.arenas = new HashSet<>();

        this.loadArenas();
    }

    /**
     * Finds an arena
     *
     * @param name the name of the arena
     * @return the arena
     */
    public Arena find(String name) {
        return this.arenas.stream()
                .filter(arena -> arena.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Loads arenas from redis
     */
    private void loadArenas() {
        this.jedis.keys("arena@*").forEach(key -> {
            String value = jedis.get(key);
            Arena arena = this.gson.fromJson(value, Arena.class);

            this.arenas.add(arena);
        });
    }

    /**
     * Saves an arena
     *
     * @param arena the arena
     */
    public void saveArena(Arena arena) {
        if(this.find(arena.getName()) == null) {
            this.getArenas().add(arena);
        }

        jedis.set("arena@" + arena.getName(), this.gson.toJson(arena));
    }

    /**
     * Deletes an arena
     *
     * @param arena the arena
     */
    public void deleteArena(Arena arena) {
        this.arenas.remove(this.find(arena.getName()));

        jedis.del("arena@" + arena.getName());
    }

    /**
     * Close the service
     */
    @Override
    public void close() {
        this.arenas.forEach(this::saveArena);
    }

}
