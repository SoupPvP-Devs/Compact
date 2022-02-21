package me.ninetyeightping.compact.grant.menus;


import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.controller.impl.grants.Grantable;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrant;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrantController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.models.impl.Rank;
import me.ninetyeightping.compact.util.Chat;
import me.ninetyeightping.compact.util.ItemBuilder;
import me.ninetyeightping.compact.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class GrantMenu extends PaginatedMenu {

    public Profile target;
    public String grantreason;
    public Long grantDuration;
    public Rank grantrank;

    public GrantMenu(Player player, Profile target) {
        super(player, "Select a Rank", 27);

        this.target = target;
    }

    public ItemStack fetchStack(Rank rank) {
        return ItemBuilder.of(Material.WOOL)
                .name(rank.getColor() + rank.getDisplayName())
                .setLore(Arrays.asList("", "&9Click to grant &f" + target.getUsername() + " " + rank.getColor() + rank.getDisplayName(), ""))
                .data(ItemBuilder.getWoolColor(rank.getColor()))
                .build();
    }

    @Override
    public void tick() {
        int i = 0;

        for (Rank rank : InjectionUtil.get(RankController.class).cache) {
            this.buttons[i++] = new Button(fetchStack(rank)).setClickAction(event -> reasonConvo(getPlayer(), rank));
        }
    }

    private void reasonConvo(Player player, Rank rank) {
        player.closeInventory();
        grantrank = rank;
        ConversationFactory factory = new ConversationFactory(Compact.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {
            public String getPromptText(ConversationContext context) {
                return Chat.format("&ePlease type a reason for this grant, or type &ccancel &eto cancel.");
            }

            public Prompt acceptInput(ConversationContext context, String input) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage(Chat.format("&cGrant process aborted."));
                    return Prompt.END_OF_CONVERSATION;
                } else {
                    String reason = input;

                    grantreason = reason;

                    Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                        durationConversation(getPlayer());
                    }, 1L);

                    return Prompt.END_OF_CONVERSATION;
                }
            }
        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");
        Conversation con = factory.buildConversation(player);
        player.beginConversation(con);
    }

    private void durationConversation(Player player) {
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Compact.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {
            public String getPromptText(ConversationContext context) {
                return Chat.format("&ePlease type a duration for this grant, (\"perm\" for permanent), or type &ccancel &eto cancel.");
            }

            public Prompt acceptInput(ConversationContext context, String input) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage(Chat.format("&cGrant process aborted."));
                    return Prompt.END_OF_CONVERSATION;
                } else {
                    long duration = TimeUtil.parseTime(input);
                    if (duration <= 0L) {
                        player.sendMessage(Chat.format("&cInvalid time, grant process aborted."));
                        return Prompt.END_OF_CONVERSATION;
                    }

                    grantDuration = duration;

                    Bukkit.getScheduler().runTaskLater(Compact.getInstance(), () -> {
                        RankGrant rankGrant = new RankGrant(UUID.fromString(target.getUuid()), player.getUniqueId(), grantreason, grantDuration, grantrank);
                        InjectionUtil.get(RankGrantController.class).create(rankGrant);
                        player.sendMessage(Chat.format("&aGranted &f" + target.getUsername() + " &athe " + grantrank.getColor() + grantrank.getDisplayName() + " &arank"));
                    }, 1L);
                    return Prompt.END_OF_CONVERSATION;
                }
            }
        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");
        Conversation con = factory.buildConversation(player);
        player.beginConversation(con);
    }

}
