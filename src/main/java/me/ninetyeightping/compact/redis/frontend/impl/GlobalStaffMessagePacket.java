package me.ninetyeightping.compact.redis.frontend.impl;

import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.redis.backend.RedisPacket;
import me.ninetyeightping.compact.util.Chat;

public class GlobalStaffMessagePacket implements RedisPacket {

    public String message;

    public GlobalStaffMessagePacket(String message) {
        this.message = message;
    }

    @Override
    public void onReceive() {
        Chat.sendStaffMessage(message);
    }
}
