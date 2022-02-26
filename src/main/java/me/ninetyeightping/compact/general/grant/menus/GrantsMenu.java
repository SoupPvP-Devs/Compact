package me.ninetyeightping.compact.general.grant.menus;

import com.google.common.collect.Lists;
import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrant;
import me.ninetyeightping.compact.models.impl.Profile;
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

public class GrantsMenu extends PaginatedMenu {
    public Profile profile;


    public GrantsMenu(Player player, Profile profile) {
        super(player, "Grants of: " + profile.getUsername(), 18);

        this.profile = profile;
    }

    public ItemStack fetchGrantStackForPlayer(RankGrant rankGrant) {
        ItemStack stack = new ItemStack(Material.WOOL);

        ItemMeta itemMeta = stack.getItemMeta();

        itemMeta.setDisplayName(Chat.format((rankGrant.isActive() ? "&a" : "&c") + new Date(rankGrant.getAddedAt())));
        addToLore(itemMeta, " ");
        addToLore(itemMeta, "&eBy: &f" + CompactAPI.INSTANCE.getColoredDisplay(rankGrant.getExecutor()));
        addToLore(itemMeta, "&eTo: &f" + CompactAPI.INSTANCE.getColoredDisplay(rankGrant.getTarget()));
        addToLore(itemMeta, "&eRank: &f" + rankGrant.getGrantable().getColor() + rankGrant.getGrantable().getDisplayName());
        addToLore(itemMeta, "&eReason: &f" + rankGrant.getReason());
        addToLore(itemMeta, "&eDuration: &f" + (rankGrant.getDuration() == Long.MAX_VALUE ? "Forever" : TimeUtil.formatDuration(rankGrant.getDuration())));
        if (!rankGrant.isActive()) {
            addToLore(itemMeta, "&eRemoved Reason: &f" + rankGrant.getRemovedReason());
            addToLore(itemMeta, "&eRemoved By: &f" + CompactAPI.INSTANCE.getColoredDisplay(rankGrant.getRemovedBy()));
        }
        addToLore(itemMeta, "&7&m----------------------");
        addToLore(itemMeta, "&cClick to remove this grant");
        addToLore(itemMeta, "");

        stack.setItemMeta(itemMeta);

        stack.setDurability((rankGrant.isActive() ? DyeColor.GREEN.getWoolData() : DyeColor.RED.getWoolData()));

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

        for (RankGrant rankGrant : profile.fetchGrants()) {

            this.buttons[i++] = new Button(fetchGrantStackForPlayer(rankGrant));
        }
    }
}
