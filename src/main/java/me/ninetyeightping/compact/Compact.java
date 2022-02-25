package me.ninetyeightping.compact;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import io.github.nosequel.menu.MenuHandler;
import lombok.Getter;
import me.ninetyeightping.compact.controller.Controller;
import me.ninetyeightping.compact.controller.impl.NetworkServerController;
import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrantController;
import me.ninetyeightping.compact.databasing.MongoConstants;
import me.ninetyeightping.compact.general.grant.GrantCommands;
import me.ninetyeightping.compact.general.heartbeat.MainHeartbeatThread;
import me.ninetyeightping.compact.general.networkserver.NetworkServerThread;
import me.ninetyeightping.compact.general.networkserver.commands.EnvironmentCommand;
import me.ninetyeightping.compact.models.impl.NetworkServer;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.profile.ProfileListener;
import me.ninetyeightping.compact.general.profile.adapt.ProfileAdapter;
import me.ninetyeightping.compact.general.punishments.commands.ForeverPunishmentCommands;
import me.ninetyeightping.compact.general.punishments.commands.PunishmentMenuCommands;
import me.ninetyeightping.compact.general.punishments.commands.TemporaryPunishmentCommands;
import me.ninetyeightping.compact.general.punishments.listeners.PunishmentJoinListener;
import me.ninetyeightping.compact.general.rank.commands.RankModificationCommands;
import me.ninetyeightping.compact.redis.backend.PacketHandler;
import me.ninetyeightping.compact.general.staff.StaffJoinAndLeaveMessageListener;
import me.vaperion.blade.Blade;
import me.vaperion.blade.bindings.impl.BukkitBindings;
import me.vaperion.blade.container.impl.BukkitCommandContainer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

public class Compact extends JavaPlugin {
    @Getter private static Compact instance;



    @Getter private static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().setLongSerializationPolicy(LongSerializationPolicy.STRING).create();


    @Getter public ProfileController profileController;
    @Getter public RankController rankController;
    @Getter public RankGrantController rankGrantController;
    @Getter public PunishmentController punishmentController;
    @Getter public NetworkServerController networkServerController;

    @Getter public NetworkServer localNetworkServer;



    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        new MenuHandler(this);
        MongoConstants.loadConnections();
        PacketHandler.init();

        //putting these here because in order to make sure that injection works I put em here. will change at some point

        profileController = new ProfileController(MongoConstants.profiles);
        rankController = new RankController(MongoConstants.ranks);
        rankGrantController = new RankGrantController(MongoConstants.rankGrants);
        punishmentController = new PunishmentController(MongoConstants.punishments);
        networkServerController = new NetworkServerController(MongoConstants.networkServer);

        if (!networkServerController.exists(getConfig().getString("server.name")))
        {
            NetworkServer networkServer = new NetworkServer(getConfig().getString("server.name"),
                    getConfig().getString("server.displayName"),
                    0,
                    true,
                    System.currentTimeMillis(),
                    System.currentTimeMillis());
            networkServer.save();
            Bukkit.getLogger().log(Level.FINE, "Created local NetworkServer because one was not found");
        }

        localNetworkServer = networkServerController.getById(getConfig().getString("server.name"));
        Bukkit.getLogger().log(Level.FINE, "Found local NetworkServer");

        getServer().getPluginManager().registerEvents(new ProfileListener(), this);
        getServer().getPluginManager().registerEvents(new PunishmentJoinListener(), this);
        getServer().getPluginManager().registerEvents(new StaffJoinAndLeaveMessageListener(), this);
        Bukkit.getLogger().log(Level.FINE, "Listeners registered");

        Bukkit.getScheduler().runTask(this, NetworkServerThread::checkForOfflineServers);
        Bukkit.getScheduler().runTask(this, MainHeartbeatThread::startHeartbeat);
        Bukkit.getLogger().log(Level.FINE, "Heartbeat and server threads started");

        Blade.of().fallbackPrefix("Compact").binding(new BukkitBindings())
                .bind(Profile.class, new ProfileAdapter())
                .containerCreator(BukkitCommandContainer.CREATOR)
                .build().register(new GrantCommands())
                .register(new RankModificationCommands())
                .register(new ForeverPunishmentCommands())
                .register(new PunishmentMenuCommands())
                .register(new TemporaryPunishmentCommands())
                .register(new EnvironmentCommand());

        Bukkit.getLogger().log(Level.FINE, "Commands registered");
    }
}
