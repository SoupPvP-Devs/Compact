package me.ninetyeightping.compact.redis.frontend.impl;

import me.ninetyeightping.compact.redis.backend.RedisPacket;
import me.ninetyeightping.compact.util.Chat;

public class GlobalAdminMessagePacket implements RedisPacket {

    public String message;

    public GlobalAdminMessagePacket(String message) {
        this.message = message;
    }


    @Override
    public void onReceive() {
        Chat.sendAdminMessage(message);
    }
}
