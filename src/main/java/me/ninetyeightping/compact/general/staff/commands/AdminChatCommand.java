package me.ninetyeightping.compact.general.staff.commands;

import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.redis.backend.PacketHandler;
import me.ninetyeightping.compact.redis.frontend.impl.GlobalAdminMessagePacket;
import me.vaperion.blade.annotation.*;
import org.bukkit.entity.Player;

public class AdminChatCommand {

    @Command(value = {"ac", "adminchat"})
    @Permission(value = "rank.admin")
    public void adminChat(@Sender Player player, @Name(value = "message") @Combined String msg){
        PacketHandler.sendToAll(new GlobalAdminMessagePacket("&c&l[Network] &7&o(" + Compact.getInstance().getLocalNetworkServer().getDisplayName() + ") &r" + CompactAPI.INSTANCE.getProfile(player.getUniqueId()).getHighestRank().getColor() + player.getName() + "&r&7: &c" + msg));
    }
}
