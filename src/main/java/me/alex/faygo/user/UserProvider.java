package me.alex.faygo.user;

import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.parametric.Provider;
import lombok.SneakyThrows;
import me.alex.data.DataPlugin;
import me.alex.data.user.User;

import java.lang.annotation.Annotation;
import java.util.List;

public class UserProvider implements Provider<User> {

    @SneakyThrows
    @Override
    public User get(CommandArgs commandArgs, List<? extends Annotation> list) {
        return DataPlugin.getDataPlugin().getDataService().getUser(commandArgs.next());
    }

    @Override
    public String getName() {
        return "user";
    }
}
