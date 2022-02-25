package me.ninetyeightping.compact.general.heartbeat;

import me.ninetyeightping.compact.Compact;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainHeartbeatThread {

    public static void startHeartbeat()
    {

        final ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();

        //ugly af class idk wtf to make it
        service.scheduleAtFixedRate(() ->
        {
            Compact.getInstance().getNetworkServerController().refresh();
            Compact.getInstance().getProfileController().refresh();
            Compact.getInstance().getPunishmentController().refresh();
            Compact.getInstance().getRankController().refresh();
            Compact.getInstance().getRankGrantController().refresh();

        }, 0L, 2L, TimeUnit.SECONDS);
    }
}