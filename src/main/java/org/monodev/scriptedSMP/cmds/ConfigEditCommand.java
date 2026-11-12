package org.monodev.scriptedSMP.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.monodev.scriptedSMP.config.ConfigEntry;
import org.monodev.scriptedSMP.config.ConfigRegistry;

import java.util.List;

public class ConfigEditCommand implements TabExecutor {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        if (!sender.hasPermission("scriptedsmp.config.edit")) {
            sender.sendMessage("You don't have permission to execute this command.");
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage("Usage: /configedit <setting> <value>");
            return false;
        }

        ConfigEntry<?> entry = ConfigRegistry.getEntries().get(args[0]);

        try {
            setValue(entry, args[1]);
        } catch (RuntimeException exception) {
            sender.sendMessage(
                    "Invalid value for setting: " + entry.key()
            );
            return false;
        }

        sender.sendMessage("Updated " + entry.key() + " to " + args[1] + ".");
        return true;
    }

    private <T> void setValue(ConfigEntry<T> entry, String input) {
        entry.value(entry.parse(input));
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        if (args.length == 1) {
            return ConfigRegistry.getEntries().keySet().stream()
                    .filter(key -> key.startsWith(args[0]))
                    .toList();
        }

        if (args.length == 2) {
            ConfigEntry<?> entry = ConfigRegistry.getEntries().get(args[0]);

            if (entry != null && entry.type() == Boolean.class) {
                return List.of("true", "false");
            }
        }

        return List.of();
    }
}