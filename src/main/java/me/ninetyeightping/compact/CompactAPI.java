package me.ninetyeightping.compact;

import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;

import java.util.UUID;

public class CompactAPI {

    public static Profile getProfile(UUID uuid) {
        return InjectionUtil.get(ProfileController.class).getById(uuid.toString());
    }

    public static UUID getConsoleUUID() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static String getColoredDisplay(UUID uuid) {
        Profile profile = getProfile(uuid);
        if (profile == null) {
            return "&cNot Found";
        } else return profile.getHighestRank().getColor() + profile.getUsername();
    }
}
