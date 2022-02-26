package me.ninetyeightping.compact.general.networkserver.commands;

import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.redis.backend.PacketHandler;
import me.ninetyeightping.compact.redis.frontend.impl.GlobalStaffMessagePacket;
import me.ninetyeightping.compact.util.Chat;
import me.vaperion.blade.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class StaffChatCommand {

    @Command(name = {"sc", "staffchat"})
    @Permission(value = "compact.staff")
    public void staffChat(@Sender CommandSender sender, @Param("message") @Combined String message) {
        PacketHandler.sendToAll(new GlobalStaffMessagePacket(Chat.format("&9&l[Network] &7&o(" + Compact.getInstance().getLocalNetworkServer().getDisplayName() + "&7&o) " + CompactAPI.getProfile(sender.getUniqueId()).getHighestRank().getColor() + sender.getName() + "&7: " + message)));

    }
}
