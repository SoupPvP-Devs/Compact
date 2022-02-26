package me.ninetyeightping.compact.general.rank.menu;

import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.models.impl.Rank;
import me.ninetyeightping.compact.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class RankListMenu extends PaginatedMenu {


    public RankListMenu(Player player) {
        super(player, "Rank List", 27);

    }

    public ItemStack fetchStack(Rank rank) {
        return ItemBuilder.of(Material.WOOL)
                .name(rank.getColor() + rank.getDisplayName())
                .setLore(Arrays.asList("", "&eDisplay Name: &r" + rank.getColor() + rank.getDisplayName(), "&eID: &f" + rank.getId(), "&ePrefix: &r" + rank.getPrefix(), "&eDisplay Weight: &f" + rank.getDisplayWeight(), "&eInternal Weight: &f" + rank.getInternalWeight(), "&eStaff: " + rank.isStaff(), ""))
                .data(ItemBuilder.getWoolColor(rank.getColor()))
                .build();
    }

    @Override
    public void tick() {
        int i = 0;

        for (Rank rank : InjectionUtil.get(RankController.class).cache) {
            this.buttons[i++] = new Button(fetchStack(rank));
        }
    }
}
