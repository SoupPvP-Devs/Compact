package me.ninetyeightping.compact;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import io.github.nosequel.menu.MenuHandler;
import lombok.Getter;
import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrantController;
import me.ninetyeightping.compact.databasing.MongoConstants;
import me.ninetyeightping.compact.grant.GrantCommands;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.profile.ProfileListener;
import me.ninetyeightping.compact.profile.adapt.ProfileAdapter;
import me.ninetyeightping.compact.punishments.commands.ForeverPunishmentCommands;
import me.ninetyeightping.compact.punishments.commands.PunishmentMenuCommands;
import me.ninetyeightping.compact.punishments.commands.TemporaryPunishmentCommands;
import me.ninetyeightping.compact.punishments.listeners.PunishmentJoinListener;
import me.ninetyeightping.compact.rank.commands.RankModificationCommands;
import me.ninetyeightping.compact.redis.backend.PacketHandler;
import me.vaperion.blade.Blade;
import me.vaperion.blade.bindings.impl.BukkitBindings;
import me.vaperion.blade.container.impl.BukkitCommandContainer;
import org.bukkit.plugin.java.JavaPlugin;

public class Compact extends JavaPlugin {
    @Getter private static Compact instance;



    @Getter private static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().setLongSerializationPolicy(LongSerializationPolicy.STRING).create();


    @Getter public ProfileController profileController;
    @Getter public RankController rankController;
    @Getter public RankGrantController rankGrantController;
    @Getter public PunishmentController punishmentController;


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

        getServer().getPluginManager().registerEvents(new ProfileListener(), this);
        getServer().getPluginManager().registerEvents(new PunishmentJoinListener(), this);

        Blade.of().fallbackPrefix("Compact").binding(new BukkitBindings())
                .bind(Profile.class, new ProfileAdapter())
                .containerCreator(BukkitCommandContainer.CREATOR)
                .build().register(new GrantCommands())
                .register(new RankModificationCommands())
                .register(new ForeverPunishmentCommands())
                .register(new PunishmentMenuCommands())
                .register(new TemporaryPunishmentCommands());
    }
}
