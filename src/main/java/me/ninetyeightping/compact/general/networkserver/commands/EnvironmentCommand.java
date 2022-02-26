package me.ninetyeightping.compact.general.networkserver.commands;

import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.util.Chat;
import me.vaperion.blade.annotation.Command;
import me.vaperion.blade.annotation.Sender;
import org.bukkit.command.CommandSender;

public class EnvironmentCommand {

    @Command(value = {"env", "environment"})
    public void env(@Sender CommandSender sender)
    {
        sender.sendMessage(Chat.format("&6&lCompact &7(Environment Check)"));
        sender.sendMessage(Chat.format(" "));
        sender.sendMessage(Chat.format("&eInternal Server ID: &f" + Compact.getInstance().getLocalNetworkServer().getId()));
        sender.sendMessage(Chat.format("&eServer Displayname: &f" + Compact.getInstance().getLocalNetworkServer().getDisplayName()));
        sender.sendMessage(Chat.format("&ePlayers: &f" + Compact.getInstance().getLocalNetworkServer().getPlayers()));
        sender.sendMessage(" ");
    }
}
