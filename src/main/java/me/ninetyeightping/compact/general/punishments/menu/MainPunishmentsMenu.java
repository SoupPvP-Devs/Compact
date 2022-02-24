package me.ninetyeightping.compact.general.punishments.menu;

import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.punishments.PunishmentType;
import me.ninetyeightping.compact.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainPunishmentsMenu extends Menu {

    public Profile target;

    public MainPunishmentsMenu(Player player, Profile target) {
        super(player, "Select a Punishment Type", 9);

        this.target = target;
    }

    @Override
    public void tick() {
        buttons[1] = new Button(Material.WOOL).setDisplayName(Chat.format("&eWarns")).setData(DyeColor.YELLOW.getWoolData()).setClickAction(event -> {
            Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                event.setCancelled(true);
                new SpecificPunishmentsMenu(getPlayer(), target, PunishmentType.WARN).updateMenu();
            },  5L);
        });
        buttons[3] = new Button(Material.WOOL).setDisplayName(Chat.format("&6Mutes")).setData(DyeColor.ORANGE.getWoolData()).setClickAction(event -> {
                    Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {

                        event.setCancelled(true);
                        new SpecificPunishmentsMenu(getPlayer(), target, PunishmentType.MUTE).updateMenu();
                    },  5L);
        });
        buttons[5] = new Button(Material.WOOL).setDisplayName(Chat.format("&cBans")).setData(DyeColor.RED.getWoolData()).setClickAction(event -> {
            Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                event.setCancelled(true);
                new SpecificPunishmentsMenu(getPlayer(), target, PunishmentType.BAN).updateMenu();
            },  5L);
        });
        buttons[7] = new Button(Material.WOOL).setDisplayName(Chat.format("&8Blacklists")).setData(DyeColor.BLACK.getWoolData()).setClickAction(event -> {
            Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                event.setCancelled(true);
                new SpecificPunishmentsMenu(getPlayer(), target, PunishmentType.BLACKLIST).updateMenu();
            },  5L);
        });
    }
}
