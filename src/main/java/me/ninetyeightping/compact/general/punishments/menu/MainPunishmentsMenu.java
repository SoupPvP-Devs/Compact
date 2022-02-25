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

    public void playNextMenu(PunishmentType punishmentType) {
        new SpecificPunishmentsMenu(getPlayer(), target, punishmentType).updateMenu();
    }

    @Override
    public void tick() {
        buttons[1] = new Button(Material.WOOL).setDisplayName(Chat.format("&eWarns")).setData(DyeColor.YELLOW.getWoolData()).setClickAction(event -> {
            getPlayer().closeInventory();
            Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                playNextMenu(PunishmentType.WARN);
            },  5L);
        });
        buttons[3] = new Button(Material.WOOL).setDisplayName(Chat.format("&6Mutes")).setData(DyeColor.ORANGE.getWoolData()).setClickAction(event -> {
            getPlayer().closeInventory();
            Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                playNextMenu(PunishmentType.MUTE);
            },  5L);
        });
        buttons[5] = new Button(Material.WOOL).setDisplayName(Chat.format("&cBans")).setData(DyeColor.RED.getWoolData()).setClickAction(event -> {
            getPlayer().closeInventory();
            Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                playNextMenu(PunishmentType.BAN);
            },  5L);
        });
        buttons[7] = new Button(Material.WOOL).setDisplayName(Chat.format("&8Blacklists")).setData(DyeColor.BLACK.getWoolData()).setClickAction(event -> {
            getPlayer().closeInventory();
            Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                playNextMenu(PunishmentType.BLACKLIST);
            },  5L);
        });
    }
}
