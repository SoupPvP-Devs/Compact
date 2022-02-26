package me.ninetyeightping.compact;

import lombok.Getter;
import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;

import java.util.UUID;

@Getter
public enum CompactAPI {

    INSTANCE;

    public Profile getProfile(UUID uuid) {
        return InjectionUtil.get(ProfileController.class).getById(uuid.toString());
    }

    public UUID getConsoleUUID() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public String getColoredDisplay(UUID uuid) {
        Profile profile = getProfile(uuid);
        if (uuid.equals(getConsoleUUID())){
            return "&4&lConsole";
        } else if (profile == null) {
            return "&cNot Found";
        } else return profile.getHighestRank().getColor() + profile.getUsername();
    }
}
