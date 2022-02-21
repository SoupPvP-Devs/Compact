package me.vaperion.blade.bindings.impl.provider;

import me.ninetyeightping.compact.Compact;
import me.vaperion.blade.argument.BladeArgument;
import me.vaperion.blade.argument.BladeProvider;
import me.vaperion.blade.command.BladeCommand;
import me.vaperion.blade.context.BladeContext;
import me.vaperion.blade.exception.BladeExitMessage;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PermissionBladeProvider implements BladeProvider<Permission> {
	@Nullable
	@Override
	public Permission provide(@NotNull BladeContext ctx, @NotNull BladeArgument arg) throws BladeExitMessage {
		return new Permission(arg.getString());
	}

	@NotNull
	@Override
	public List<String> suggest(@NotNull BladeContext context, @NotNull BladeArgument arg) throws BladeExitMessage {
		List<String> completions = new ArrayList<>();

		String input = arg.getString();
		for (Plugin plugins : Bukkit.getServer().getPluginManager().getPlugins()) {
			if (plugins instanceof JavaPlugin) {
				JavaPlugin plugin = (JavaPlugin) plugins;
				for (String command : plugin.getDescription().getCommands().keySet()) {
					try {
						String perm = plugin.getCommand(command).getPermission();
						if (perm != null && perm.toLowerCase().startsWith(input.toLowerCase())) {
							completions.add(perm);
						}
					} catch (Exception ignored) {

					}
				}
			}
		}

		return completions;
	}

}
