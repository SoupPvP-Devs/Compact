package me.ninetyeightping.compact.general.staff;


import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.redis.backend.PacketHandler;
import me.ninetyeightping.compact.redis.frontend.impl.GlobalStaffMessagePacket;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class StaffJoinAndLeaveMessageListener implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {

        Profile profile = CompactAPI.INSTANCE.getProfile(event.getPlayer().getUniqueId());

        if (profile.getHighestRank().isStaff()) {

            PacketHandler.sendToAll(new GlobalStaffMessagePacket("&9&l[Network] &r" + CompactAPI.INSTANCE.getColoredDisplay(event.getPlayer().getUniqueId()) + " &bhas &ajoined &bthe network"));
        }
    }
}
