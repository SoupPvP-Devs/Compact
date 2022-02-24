package me.ninetyeightping.compact.general.profile;

import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ProfileListener implements Listener {

    @EventHandler
    public void asyncJoin(AsyncPlayerPreLoginEvent event) {
        if (!InjectionUtil.get(ProfileController.class).exists(event.getUniqueId().toString())) {

            InjectionUtil.get(ProfileController.class).create(new Profile(event.getUniqueId().toString(), event.getName(), new JSONObject(), new ArrayList<>()));
        }
    }
}
