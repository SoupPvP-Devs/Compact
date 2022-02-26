package me.ninetyeightping.compact.general.grant;

import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrant;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrantController;
import me.ninetyeightping.compact.general.grant.menus.GrantMenu;
import me.ninetyeightping.compact.general.grant.menus.GrantsMenu;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.models.impl.Rank;
import me.ninetyeightping.compact.util.Chat;
import me.ninetyeightping.compact.util.TimeUtil;
import me.vaperion.blade.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GrantCommands {

    @Command(name = "grant")
    @Permission(value = "grants.admin")
    public void grant(@Sender Player player, @Param("target")Profile target) {
        new GrantMenu(player, target).updateMenu();
    }

    @Command(name = "grants")
    @Permission(value = "grants.admin")
    public void grants(@Sender Player player, @Param("target")Profile target) {
        new GrantsMenu(player, target).updateMenu();
    }

    @Command(name = "ogrant")
    @Permission(value = "grants.admin")
    public void ogrant(@Sender CommandSender player, @Param("target")Profile target, @Param("rank")String rank, @Param("duration")String duration, @Param("reason")@Combined String reason) {
        Rank grantrank = InjectionUtil.get(RankController.class).getById(rank);

        if (grantrank == null) {
            player.sendMessage(Chat.format("&cRank does not exist"));
            return;
        }

        RankGrant rankGrant = new RankGrant(UUID.fromString(target.getUuid()), (player instanceof Player ? ((Player) player).getUniqueId() : CompactAPI.INSTANCE.getConsoleUUID()), reason, TimeUtil.parseTime(duration), grantrank);

        InjectionUtil.get(RankGrantController.class).create(rankGrant);
        player.sendMessage(Chat.format("&aGranted " + target.getUsername() + " " + grantrank.getDisplayName()));
    }
}
