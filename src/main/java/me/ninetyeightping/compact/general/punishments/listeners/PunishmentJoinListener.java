package me.ninetyeightping.compact.general.punishments.listeners;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.punishments.PunishmentType;
import me.ninetyeightping.compact.util.Chat;
import me.ninetyeightping.compact.util.TimeUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PunishmentJoinListener implements Listener {

    @EventHandler
    public void tryToJoinWhileBlacklist(AsyncPlayerPreLoginEvent event) {

        Profile profile = CompactAPI.INSTANCE.getProfile(event.getUniqueId());

        if (profile == null) return;

        if (profile.hasActivePunishmentByType(PunishmentType.BLACKLIST)) {

            Punishment punishment = profile.getFirstPunishmentByType(PunishmentType.BLACKLIST);

            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(Chat.format("&cYou are currently blacklisted\n&cExpires In: " + (punishment.getDuration() == Long.MAX_VALUE ? "Never" : TimeUtil.formatDuration(punishment.getRemainingTime()))));


        }


    }

    @EventHandler
    public void tryToJoinWhileBanned(AsyncPlayerPreLoginEvent event) {

        Profile profile = CompactAPI.INSTANCE.getProfile(event.getUniqueId());

        if (profile == null) return;

        if (profile.hasActivePunishmentByType(PunishmentType.BAN)) {

            Punishment punishment = profile.getFirstPunishmentByType(PunishmentType.BAN);

            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(Chat.format("&cYou are currently banned\n&cExpires In: " + (punishment.getDuration() == Long.MAX_VALUE ? "Never" : TimeUtil.formatDuration(punishment.getRemainingTime()))));


        }


    }
}
