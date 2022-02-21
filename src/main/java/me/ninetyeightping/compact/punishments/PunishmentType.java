package me.ninetyeightping.compact.punishments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@AllArgsConstructor
public enum PunishmentType {

    BLACKLIST("Blacklist", "blacklisted", "unblacklisted", ChatColor.DARK_RED),
    BAN("Ban", "banned", "unbanned", ChatColor.RED),
    MUTE("Mute", "muted", "unmuted", ChatColor.GOLD),
    WARN("Warn", "warned", "unwarned", ChatColor.YELLOW);

    private final String display;
    private final String added;
    private final String removed;
    private final ChatColor chatColor;
}
