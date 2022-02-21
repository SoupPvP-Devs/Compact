package me.ninetyeightping.compact.redis.frontend.impl;

import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.redis.backend.RedisPacket;
import me.ninetyeightping.compact.util.Chat;
import me.ninetyeightping.compact.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PunishmentKickPacket implements RedisPacket {

    public Punishment punishment;

    public PunishmentKickPacket(Punishment punishment) {
        this.punishment = punishment;
    }

    @Override
    public void onReceive() {
        Player bukkitplayer = Bukkit.getPlayer(punishment.getTarget());

        if (bukkitplayer == null) return;

        bukkitplayer.kickPlayer(Chat.format("&cYou are currently " + punishment.getGrantable().getAdded() + "\n&cExpires In: " + (punishment.getDuration() == Long.MAX_VALUE ? "Forever" : TimeUtil.formatDuration(punishment.getDuration()))));
    }
}

