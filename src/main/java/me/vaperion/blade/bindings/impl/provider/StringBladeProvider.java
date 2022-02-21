package me.vaperion.blade.bindings.impl.provider;

import me.vaperion.blade.argument.BladeArgument;
import me.vaperion.blade.argument.BladeProvider;
import me.vaperion.blade.context.BladeContext;
import me.vaperion.blade.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StringBladeProvider implements BladeProvider<String[]> {
    @Nullable
    @Override
    public String[] provide(@NotNull BladeContext ctx, @NotNull BladeArgument arg) throws BladeExitMessage {
        return arg.getString().split(",");
    }

    @NotNull
    @Override
    public List<String> suggest(@NotNull BladeContext context, @NotNull BladeArgument arg) throws BladeExitMessage {
        return new ArrayList<>();
    }

}
