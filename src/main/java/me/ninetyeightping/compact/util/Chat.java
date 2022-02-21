package me.ninetyeightping.compact.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Chat {

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void sendStaffMessage(String str) {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("compact.staff")).forEach(player -> player.sendMessage(Chat.format(str)));
    }
}
