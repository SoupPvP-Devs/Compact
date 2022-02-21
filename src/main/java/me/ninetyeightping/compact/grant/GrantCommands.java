package me.ninetyeightping.compact.grant;

import io.github.nosequel.menu.Menu;
import me.ninetyeightping.compact.grant.menus.GrantMenu;
import me.ninetyeightping.compact.grant.menus.GrantsMenu;
import me.ninetyeightping.compact.models.impl.Profile;
import me.vaperion.blade.annotation.Command;
import me.vaperion.blade.annotation.Param;
import me.vaperion.blade.annotation.Permission;
import me.vaperion.blade.annotation.Sender;
import org.bukkit.entity.Player;

public class GrantCommands {

    @Command(name = "grant")
    @Permission(value = "grants.admin")
    public void grant(@Sender Player player, @Param("target")Profile target) {
        new GrantMenu(player, target).updateMenu();
    }

    @Command(name = "grants")
    @Permission(value = "grants.admin")
    public void grants(@Sender Player player, @Param("target")Profile target) {
        new GrantsMenu(player, target).updateMenu();
    }
}
