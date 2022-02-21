package me.ninetyeightping.compact.profile.adapt;

import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.vaperion.blade.argument.BladeArgument;
import me.vaperion.blade.argument.BladeProvider;
import me.vaperion.blade.context.BladeContext;
import me.vaperion.blade.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProfileAdapter implements BladeProvider<Profile> {
    @Override
    public @Nullable Profile provide(@NotNull BladeContext context, @NotNull BladeArgument argument) throws BladeExitMessage {
        return InjectionUtil.get(ProfileController.class).getByName(argument.getString());
    }
}
