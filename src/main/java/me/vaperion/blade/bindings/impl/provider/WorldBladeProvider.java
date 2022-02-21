package me.vaperion.blade.bindings.impl.provider;

import me.vaperion.blade.argument.BladeArgument;
import me.vaperion.blade.argument.BladeProvider;
import me.vaperion.blade.context.BladeContext;
import me.vaperion.blade.exception.BladeExitMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorldBladeProvider implements BladeProvider<World> {
    @Nullable
    @Override
    public World provide(@NotNull BladeContext ctx, @NotNull BladeArgument arg) throws BladeExitMessage {
        World world = getGameMode(arg.getString());

        if (world == null && !arg.getParameter().ignoreFailedArgumentParse())
            throw new BladeExitMessage("Error: '" + arg.getString() + "' is not a valid world.");

        return world;
    }

    @NotNull
    @Override
    public List<String> suggest(@NotNull BladeContext context, @NotNull BladeArgument arg) throws BladeExitMessage {
        String input = arg.getString().toUpperCase(Locale.ROOT);
        List<String> completions = new ArrayList<>();

        for (World mode : Bukkit.getWorlds()) {
            if (mode.getName().startsWith(input)) {
                completions.add(mode.getName());
            }
        }

        return completions;
    }

    @Nullable
    private World getGameMode(String input) {

        for (World world : Bukkit.getWorlds()) {
            if (world.getName().equals(input)) {
                return world;
            }
        }

        return null;
    }
}
