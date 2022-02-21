package me.ninetyeightping.compact.punishments.commands;

import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.punishments.PunishmentType;
import me.vaperion.blade.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ForeverPunishmentCommands {

    @Command(name = "ban")
    @Permission(value = "compact.ban")
    public void ban(@Sender CommandSender sender, @Param("target")Profile target, @Flag(value = 's', description = "Silently ban the player") boolean silent, @Param("reason") @Combined String reason) {

        Punishment punishment = new Punishment(UUID.randomUUID(), (sender instanceof Player ? ((Player) sender).getUniqueId() : CompactAPI.getConsoleUUID()), UUID.fromString(target.getUuid()), reason, Long.MAX_VALUE, PunishmentType.BAN);

        InjectionUtil.get(PunishmentController.class).dispatch(punishment, silent);

    }
}
