package me.vaperion.blade.bindings.impl;

import me.vaperion.blade.bindings.Binding;
import me.vaperion.blade.bindings.impl.provider.*;
import me.vaperion.blade.service.BladeCommandService;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Pattern;

public class BukkitBindings implements Binding {

    public static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}");

    @Override
    public void bind(@NotNull BladeCommandService commandService) {
        commandService.bindProvider(Player.class, new PlayerBladeProvider());
        commandService.bindProvider(OfflinePlayer.class, new OfflinePlayerBladeProvider());
        commandService.bindProvider(Permission.class, new PermissionBladeProvider());
        commandService.bindProvider(World.class, new WorldBladeProvider());
        commandService.bindProvider(String[].class, new StringBladeProvider());
        commandService.bindProvider(GameMode.class, new GameModeBladeProvider());
    }

}
