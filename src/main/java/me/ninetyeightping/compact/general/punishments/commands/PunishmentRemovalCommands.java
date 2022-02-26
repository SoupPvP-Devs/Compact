package me.ninetyeightping.compact.general.punishments.commands;

import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.general.punishments.PunishmentType;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.util.Chat;
import me.vaperion.blade.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PunishmentRemovalCommands {

    @Command(value = "unban")
    @Permission(value = "compact.unban")
    public void unban(@Sender CommandSender sender, @Name("target") Profile target, @Flag(value = 's', description = "Silently unbans the player") boolean silent, @Name("reason") @Combined String reason) {

        Punishment punishment = target.getFirstPunishmentByType(PunishmentType.BAN);

        if (punishment == null) {
            sender.sendMessage(Chat.format("&cNo active punishment by this type"));
            return;
        }
    }

    @Command(value = "unmute")
    @Permission(value = "compact.unmute")
    public void unMute(@Sender CommandSender sender, @Name("target") Profile target, @Flag(value = 's', description = "Silently unmutes the player") boolean silent, @Name("reason") @Combined String reason) {
        Punishment punishment = target.getFirstPunishmentByType(PunishmentType.MUTE);

        if (punishment == null) {
            sender.sendMessage(Chat.format("&cNo active punishment by this type"));
        }


    }

    @Command(value = "unblacklist")
    @Permission(value = "compact.unblacklist")
    public void unBlacklist(@Sender CommandSender sender, @Name("target") Profile target, @Flag(value = 's', description = "Silently unblacklists the player") boolean silent, @Name("reason") @Combined String reason) {
        Punishment punishment = target.getFirstPunishmentByType(PunishmentType.BLACKLIST);

        if (punishment == null) {
            sender.sendMessage(Chat.format("&cNo active punishment by this type"));
        }


    }

}
