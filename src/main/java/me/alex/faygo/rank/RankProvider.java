package me.alex.faygo.rank;

import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.parametric.Provider;
import lombok.SneakyThrows;
import me.alex.data.rank.Rank;

import java.lang.annotation.Annotation;
import java.util.List;

public class RankProvider implements Provider<Rank> {

    @SneakyThrows
    @Override
    public Rank get(CommandArgs commandArgs, List<? extends Annotation> list) {
        return Rank.valueOf(commandArgs.next().toUpperCase());
    }

    @Override
    public String getName() {
        return "rank";
    }
}
