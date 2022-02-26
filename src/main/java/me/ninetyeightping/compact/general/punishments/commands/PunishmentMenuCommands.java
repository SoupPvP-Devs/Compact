package me.ninetyeightping.compact.general.punishments.commands;

import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.punishments.menu.MainPunishmentsMenu;
import me.vaperion.blade.annotation.Command;
import me.vaperion.blade.annotation.Name;
import me.vaperion.blade.annotation.Permission;
import me.vaperion.blade.annotation.Sender;
import org.bukkit.entity.Player;

public class PunishmentMenuCommands {

    @Command(value = {"cp", "c", "history", "checkpunishments"})
    @Permission(value = "compact.history")
    public void c(@Sender Player player, @Name(value = "target")Profile target) {
        new MainPunishmentsMenu(player, target).updateMenu();
    }
}
