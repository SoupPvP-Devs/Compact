package me.ninetyeightping.compact.general.rank.commands;

import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.impl.Rank;
import me.ninetyeightping.compact.util.Chat;
import me.vaperion.blade.annotation.*;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class RankModificationCommands {

    @Command(value = "rank create")
    @Permission(value = "rank.admin")
    public void create(@Sender CommandSender sender, @Name("name")String name) {

        if (InjectionUtil.get(RankController.class).exists(name)) {
            sender.sendMessage(Chat.format("&cRank already exists"));
            return;
        }

        Rank rank = new Rank(name, "&f", name, 0, 0, false, "", new ArrayList<>(), new ArrayList<>());
        InjectionUtil.get(RankController.class).create(rank);

        sender.sendMessage(Chat.format("&aCreated " + name));
    }



    @Command(value = "rank data modality")
    @Permission(value = "rank.admin")
    public void modality(@Sender CommandSender sender, @Name("rank")String name, @Name("modality")String modality, @Name("arg")@Combined String arg) {

        if (!InjectionUtil.get(RankController.class).exists(name)) {
            sender.sendMessage(Chat.format("&cRank doesn't exists"));
            return;
        }

        Rank rank = InjectionUtil.get(RankController.class).getById(name);

        switch (modality) {
            case "prefix":
                rank.setPrefix(arg);
                rank.save();
                sender.sendMessage(Chat.format("&aUpdated prefix"));
                break;


            case "staff":
                rank.setStaff(Boolean.parseBoolean(arg));
                rank.save();
                sender.sendMessage(Chat.format("&aUpdated staff status"));
                break;

            case "color":
                rank.setColor(arg);
                rank.save();
                sender.sendMessage(Chat.format("&aUpdated color"));
                break;

            case "internalweight":
                rank.setInternalWeight(Integer.parseInt(arg));
                rank.save();
                sender.sendMessage(Chat.format("&aUpdated internal weight"));
                break;

        }

        }
    }
