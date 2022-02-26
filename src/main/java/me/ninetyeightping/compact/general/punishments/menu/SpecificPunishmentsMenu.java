package me.ninetyeightping.compact.general.punishments.menu;

import com.google.common.collect.Lists;
import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.general.punishments.PunishmentType;
import me.ninetyeightping.compact.util.Chat;
import me.ninetyeightping.compact.util.TimeUtil;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SpecificPunishmentsMenu extends PaginatedMenu {

    public Profile target;
    public PunishmentType type;

    public SpecificPunishmentsMenu(Player player, Profile target, PunishmentType type) {
        super(player, type.getDisplay() + "s", 27);

        this.target = target;
        this.type = type;
    }

    public ItemStack fetchGrantStackForPlayer(Punishment punishment) {
        ItemStack stack = new ItemStack(Material.WOOL);

        ItemMeta itemMeta = stack.getItemMeta();

        itemMeta.setDisplayName(Chat.format((punishment.isActive() ? "&a" : "&c") + new Date(punishment.getAddedAt())));
        addToLore(itemMeta, " ");
        addToLore(itemMeta, "&eBy: &f" + CompactAPI.INSTANCE.getColoredDisplay(punishment.getExecutor()));
        addToLore(itemMeta, "&eTo: &f" + CompactAPI.INSTANCE.getColoredDisplay(punishment.getTarget()));
        addToLore(itemMeta, "&eReason: &f" + punishment.getReason());
        addToLore(itemMeta, "&eDuration: &f" + (punishment.getDuration() == Long.MAX_VALUE ? "Forever" : TimeUtil.formatDuration(punishment.getDuration())));
        if (punishment.isActive() && !punishment.getDuration() == "Forever") {
            addToLore(itemMeta, "&eRemaining: &f" + punishment.getRemainingTime());
        }
        if (!punishment.isActive()) {
            addToLore(itemMeta, "&eRemoved Reason: &f" + punishment.getRemovedReason());
            addToLore(itemMeta, "&eRemoved By: &f" + CompactAPI.INSTANCE.getColoredDisplay(punishment.getRemovedBy()));
        }
        addToLore(itemMeta, " ");

        stack.setItemMeta(itemMeta);

        stack.setDurability((punishment.isActive() ? DyeColor.GREEN.getWoolData() : DyeColor.RED.getWoolData()));

        return stack;
    }

    public void addToLore(ItemMeta itemMeta, String ... parts) {
        List lore;
        ItemMeta meta = itemMeta;
        if ((lore = meta.getLore()) == null) {
            lore = Lists.newArrayList();
        }
        lore.addAll(Arrays.stream(parts).map(part -> ChatColor.translateAlternateColorCodes('&', part)).collect(Collectors.toList()));
        meta.setLore(lore);

    }

    @Override
    public void tick() {
        int i = 0;
        for (Punishment punishment : target.getAllPunishmentsByType(type)) {
            buttons[i++] = new Button(fetchGrantStackForPlayer(punishment));
        }
    }
}
