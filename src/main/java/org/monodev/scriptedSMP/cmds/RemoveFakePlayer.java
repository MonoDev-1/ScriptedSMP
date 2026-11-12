package org.monodev.scriptedSMP.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.api.events.fakeplayer.FakePlayerLeaveEvent;
import org.monodev.scriptedSMP.bots.FakePlayer;
import org.monodev.scriptedSMP.bots.FakePlayerManager;

import java.util.Map;
import java.util.UUID;

public class RemoveFakePlayer implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Usage: /scriptedremovefakeplayer <name>", NamedTextColor.YELLOW));
            return true;
        }

        if (!sender.hasPermission("scriptedsmp.command.manage_fake_players")) {
            sender.sendMessage(Component.text(
                    "You do not have permission to execute this command.",
                    NamedTextColor.RED
            ));
            return true;
        }

        String targetName = args[0];
        UUID targetUUID = null;

        // Search active bots by name
        for (Map.Entry<UUID, FakePlayer> entry : ScriptedSMP.instance.getFakePlayerManager().getActiveBots().entrySet()) {
            if (entry.getValue().getBukkitEntity().getName().equalsIgnoreCase(targetName)) {
                targetUUID = entry.getKey();
                break;
            }
        }

        if (targetUUID != null) {
            ScriptedSMP.instance.getFakePlayerManager().removeFakePlayer(targetUUID, FakePlayerLeaveEvent.Cause.SCRIPTEDSMP);
            sender.sendMessage(Component.text("Removed fake player: " + targetName, NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Could not find an active fake player named " + targetName, NamedTextColor.RED));
        }

        return true;
    }
}