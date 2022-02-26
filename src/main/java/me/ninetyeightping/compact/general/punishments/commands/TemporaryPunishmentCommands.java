package me.ninetyeightping.compact.general.punishments.commands;

import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.punishments.PunishmentType;
import me.ninetyeightping.compact.util.TimeUtil;
import me.vaperion.blade.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TemporaryPunishmentCommands {

    @Command(name = {"tempban"})
    @Permission(value = "compact.tempban")
    public void tban(@Sender CommandSender sender, @Param("target") Profile target, @Flag(value = 's', description = "Silently tempban the player") boolean silent, @Param("duration")String timestring, @Param("reason") @Combined String reason) {

        Punishment punishment = new Punishment(UUID.randomUUID(), UUID.fromString(target.getUuid()), (sender instanceof Player ? ((Player) sender).getUniqueId() : CompactAPI.INSTANCE.getConsoleUUID()), reason, TimeUtil.parseTime(timestring), PunishmentType.BAN);

        InjectionUtil.get(PunishmentController.class).dispatch(punishment, silent);

    }

    @Command(name = {"tempmute"})
    @Permission(value = "compact.tempmute")
    public void tmute(@Sender CommandSender sender, @Param("target") Profile target, @Flag(value = 's', description = "Silently tempmute the player") boolean silent, @Param("duration")String timestring, @Param("reason") @Combined String reason) {

        Punishment punishment = new Punishment(UUID.randomUUID(), UUID.fromString(target.getUuid()), (sender instanceof Player ? ((Player) sender).getUniqueId() : CompactAPI.INSTANCE.getConsoleUUID()), reason, TimeUtil.parseTime(timestring), PunishmentType.MUTE);

        InjectionUtil.get(PunishmentController.class).dispatch(punishment, silent);

    }
}
