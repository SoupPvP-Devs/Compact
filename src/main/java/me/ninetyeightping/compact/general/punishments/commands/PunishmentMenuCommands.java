package me.ninetyeightping.compact.general.punishments.commands;

import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.punishments.menu.MainPunishmentsMenu;
import me.vaperion.blade.annotation.Command;
import me.vaperion.blade.annotation.Param;
import me.vaperion.blade.annotation.Permission;
import me.vaperion.blade.annotation.Sender;
import org.bukkit.entity.Player;

public class PunishmentMenuCommands {

    @Command(name = {"cp", "c", "history", "checkpunishments"})
    @Permission(value = "compact.history")
    public void c(@Sender Player player, @Param(value = "target")Profile target) {
        new MainPunishmentsMenu(player, target).updateMenu();
    }
}
