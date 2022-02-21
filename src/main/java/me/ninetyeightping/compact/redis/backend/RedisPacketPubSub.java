package me.ninetyeightping.compact.redis.backend;

import lombok.NoArgsConstructor;
import me.ninetyeightping.compact.Compact;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPubSub;

@NoArgsConstructor
public class RedisPacketPubSub extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        Class<?> packetClass;
        int packetMessageSplit = message.indexOf("||");
        String packetClassStr = message.substring(0, packetMessageSplit);
        String messageJson = message.substring(packetMessageSplit + "||".length());
        try {
            packetClass = Class.forName(packetClassStr);
        }
        catch (ClassNotFoundException ignored) {
            return;
        }
        RedisPacket packet = (RedisPacket) Compact.getGson().fromJson(messageJson, packetClass);
        if (Compact.getInstance().isEnabled()) {
            Bukkit.getScheduler().runTask(Compact.getInstance(), packet::onReceive);
        }
    }
}