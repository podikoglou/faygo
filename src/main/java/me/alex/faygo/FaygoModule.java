package me.alex.faygo;

import app.ashcon.intake.parametric.AbstractModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.data.rank.Rank;
import me.alex.data.user.User;
import me.alex.faygo.arena.Arena;
import me.alex.faygo.arena.ArenaProvider;
import me.alex.faygo.ladder.Ladder;
import me.alex.faygo.ladder.LadderProvider;
import me.alex.faygo.rank.RankProvider;
import me.alex.faygo.user.UserProvider;

@RequiredArgsConstructor
public class FaygoModule extends AbstractModule {

    @NonNull
    private FaygoPlugin plugin;

    @Override
    protected void configure() {
        this.bind(FaygoPlugin.class).toInstance(this.plugin);
        this.bind(User.class).toProvider(new UserProvider());
        this.bind(Rank.class).toProvider(new RankProvider());
        this.bind(Arena.class).toProvider(new ArenaProvider());
        this.bind(Ladder.class).toProvider(new LadderProvider());
    }

}
