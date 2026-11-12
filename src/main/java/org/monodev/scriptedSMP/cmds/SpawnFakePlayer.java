package org.monodev.scriptedSMP.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.bots.FakePlayer;
import org.monodev.scriptedSMP.bots.FakePlayerManager;

public class SpawnFakePlayer implements CommandExecutor {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        if (!sender.hasPermission("scriptedsmp.command.manage_fake_players")) {
            sender.sendMessage(Component.text(
                    "You do not have permission to execute this command.",
                    NamedTextColor.RED
            ));
            return true;
        }

        CommandSender actualSender = sender;
        if (sender instanceof ProxiedCommandSender proxy) {
            actualSender = proxy.getCallee();
        }

        Location loc;
        if (actualSender instanceof Entity entity) {
            loc = entity.getLocation();
        } else if (actualSender instanceof BlockCommandSender blockSender) {
            loc = blockSender.getBlock().getLocation().add(0.5, 1, 0.5);
        } else {
            sender.sendMessage(Component.text(
                    "You must execute this command as an entity or command block.",
                    NamedTextColor.RED
            ));
            return true;
        }

        if (args.length == 0) {
            FakePlayer fakePlayer = ScriptedSMP.instance.getFakePlayerManager().spawnFakePlayer(loc);
            if (fakePlayer == null) {
                sender.sendMessage(Component.text(
                        "Failed to spawn fake player. A custom plugin might have cancelled this event",
                        NamedTextColor.RED
                ));
                return true;
            }
            sender.sendMessage(Component.text(
                    "Spawned fake player with a random nickname.",
                    NamedTextColor.GREEN
            ));
            return true;
        }

        String name = args[0];
        FakePlayer fakePlayer = ScriptedSMP.instance.getFakePlayerManager().spawnFakePlayer(name, loc);
        if (fakePlayer == null) {
            sender.sendMessage(Component.text(
                    "Failed to spawn fake player. A custom plugin might have cancelled this event",
                    NamedTextColor.RED
            ));
            return true;
        }
        sender.sendMessage(Component.text(
                "Spawned fake player: " + name,
                NamedTextColor.GREEN
        ));

        return true;
    }
}
