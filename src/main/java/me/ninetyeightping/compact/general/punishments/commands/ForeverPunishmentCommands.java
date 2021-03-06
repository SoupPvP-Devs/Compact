package me.ninetyeightping.compact.general.punishments.commands;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.punishments.PunishmentType;
import me.vaperion.blade.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ForeverPunishmentCommands {


    @Command(value = "warn")
    @Permission(value = "compact.warn")
    public void warn(@Sender CommandSender sender, @Name("target")Profile target, @Flag(value = 's', description = "Silently warns the player") boolean silent, @Name("reason") @Combined String reason) {

        Punishment punishment = new Punishment(UUID.randomUUID(), UUID.fromString(target.getUuid()), (sender instanceof Player ? ((Player) sender).getUniqueId() : CompactAPI.INSTANCE.getConsoleUUID()), reason, Long.MAX_VALUE, PunishmentType.WARN);

        InjectionUtil.get(PunishmentController.class).dispatch(punishment, silent);




    }


    @Command(value = "mute")
    @Permission(value = "compact.mute")
    public void mute(@Sender CommandSender sender, @Name("target")Profile target, @Flag(value = 's', description = "Silently mute the player") boolean silent, @Name("reason") @Combined String reason) {

        Punishment punishment = new Punishment(UUID.randomUUID(), UUID.fromString(target.getUuid()), (sender instanceof Player ? ((Player) sender).getUniqueId() : CompactAPI.INSTANCE.getConsoleUUID()), reason, Long.MAX_VALUE, PunishmentType.MUTE);

        InjectionUtil.get(PunishmentController.class).dispatch(punishment, silent);

    }


    @Command(value = "ban")
    @Permission(value = "compact.ban")
    public void ban(@Sender CommandSender sender, @Name("target")Profile target, @Flag(value = 's', description = "Silently ban the player") boolean silent, @Name("reason") @Combined String reason) {

        Punishment punishment = new Punishment(UUID.randomUUID(), UUID.fromString(target.getUuid()), (sender instanceof Player ? ((Player) sender).getUniqueId() : CompactAPI.INSTANCE.getConsoleUUID()), reason, Long.MAX_VALUE, PunishmentType.BAN);

        InjectionUtil.get(PunishmentController.class).dispatch(punishment, silent);

    }

    @Command(value = "blacklist")
    @Permission(value = "compact.blacklist")
    public void blacklist(@Sender CommandSender sender, @Name("target")Profile target, @Flag(value = 's', description = "Silently blacklist the player") boolean silent, @Name("reason") @Combined String reason) {

        Punishment punishment = new Punishment(UUID.randomUUID(), UUID.fromString(target.getUuid()), (sender instanceof Player ? ((Player) sender).getUniqueId() : CompactAPI.INSTANCE.getConsoleUUID()), reason, Long.MAX_VALUE, PunishmentType.BLACKLIST);

        InjectionUtil.get(PunishmentController.class).dispatch(punishment, silent);

    }
}
