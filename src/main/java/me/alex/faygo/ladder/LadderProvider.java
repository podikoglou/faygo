package me.alex.faygo.ladder;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.parametric.Provider;
import app.ashcon.intake.parametric.ProvisionException;
import me.alex.faygo.FaygoPlugin;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

public class LadderProvider implements Provider<Ladder> {
    @Nullable
    @Override
    public Ladder get(CommandArgs commandArgs, List<? extends Annotation> list) throws ArgumentException, ProvisionException {
        return FaygoPlugin.getFaygo().getLadderService().find(commandArgs.next());
    }

    @Override
    public String getName() {
        return "ladder";
    }
}
