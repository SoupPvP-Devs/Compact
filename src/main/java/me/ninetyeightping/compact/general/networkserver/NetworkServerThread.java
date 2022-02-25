package me.ninetyeightping.compact.general.networkserver;

import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.NetworkServerController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.NetworkServer;
import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class NetworkServerThread {

    public static void checkForOfflineServers()
    {

        final ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(() -> {
            final NetworkServerController networkServerController = InjectionUtil
                    .get(NetworkServerController.class);

            for (NetworkServer networkServer : networkServerController.cache)
            {
                if (System.currentTimeMillis() - networkServer.lastResponded >= TimeUnit.SECONDS.toMillis(10) && networkServer.responding) {
                    Bukkit.getLogger().log(Level.SEVERE, "Server " + networkServer.displayName + " has taken more than 10 seconds to respond.");
                    networkServer.responding = false;
                    return;
                }


                networkServer.responding = true;
                networkServer.lastResponded = System.currentTimeMillis();

                networkServer.players = Bukkit.getOnlinePlayers().size();


                networkServer.lastHeartbeatRan = System.currentTimeMillis();

                networkServer.save();
            }
        }, 0L, 5L, TimeUnit.SECONDS);
    }
}
