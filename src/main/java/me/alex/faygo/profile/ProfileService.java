package me.alex.faygo.profile;

import lombok.Getter;
import me.alex.data.DataPlugin;
import me.alex.faygo.service.Service;

import java.util.HashSet;

@Getter
public class ProfileService implements Service {

    private HashSet<Profile> profiles;

    /**
     * Open the service
     */
    @Override
    public void open() {
        this.profiles = new HashSet<>();
    }

    /**
     * Finds the profile in the cache
     *
     * @param name the the name of the player
     * @return the profile found
     */
    public Profile find(String name) {
        return this.profiles.stream()
                .filter(profile -> profile.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> this.create(name));
    }

    /**
     * Create the profile
     *
     * @param name the name of the profile
     * @return the profile created
     */
    public Profile create(String name) {
        /* create the profile */
        Profile profile = new Profile(DataPlugin.getDataPlugin().getDataService().getUser(name));

        /* add the profile to the cache */
        this.profiles.add(profile);

        return profile;
    }

    /**
     * Close the service
     */
    @Override
    public void close() { }
}
