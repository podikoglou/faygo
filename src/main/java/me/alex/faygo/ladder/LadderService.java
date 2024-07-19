package me.alex.faygo.ladder;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.data.DataPlugin;
import me.alex.faygo.FaygoPlugin;
import me.alex.faygo.service.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;

@RequiredArgsConstructor
@Data
public class LadderService implements Service {

    @NonNull
    private FaygoPlugin plugin;
    private HashSet<Ladder> ladders;
    private Jedis jedis;
    private Gson gson;

    /**
     * Open the service
     */
    @Override
    public void open() {
        this.ladders = new HashSet<>();
        this.gson = new Gson();
        this.jedis = DataPlugin.getDataPlugin().getDataService().getJedis();
        this.loadLadders();
    }

    /**
     * Finds a ladder
     *
     * @param name the name of the ladder
     * @return the ladder
     */
    public Ladder find(String name) {
        return this.ladders.stream()
                .filter(ladder -> ladder.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Deletes a ladder
     *
     * @param ladder the ladder
     */
    public void delete(Ladder ladder) {
        this.ladders.remove(ladder);
        this.jedis.del("ladder@" + ladder.getName());
    }

    /**
     * Saves a ladder
     *
     * @param ladder the ladder
     */
    public void saveLadder(Ladder ladder) {
        if(this.find(ladder.getName()) == null) {
            this.ladders.add(ladder);
        }

        this.jedis.set("ladder@" + ladder.getName(), this.gson.toJson(ladder));
    }

    /**
     * Loads ladders from redis
     */
    private void loadLadders() {
        this.jedis.keys("ladder@*").forEach(key -> {
            String value = jedis.get(key);
            Ladder ladder = this.gson.fromJson(value, Ladder.class);

            this.ladders.add(ladder);
        });
    }

    /**
     * Close the service
     */
    @Override
    public void close() {
        this.ladders.forEach(this::saveLadder);
    }


}
