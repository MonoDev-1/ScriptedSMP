package org.monodev.scriptedSMP.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.monodev.scriptedSMP.bots.NicknameGenerator;

public class NicknameGenerateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage("hi :3 - dev");
        }

        sender.sendMessage("Generated Nickname: " + NicknameGenerator.genNickname());
        if (sender.hasPermission("scriptedsmp.command.manage_fake_players")) {
            sender.sendMessage("Tip: /spawnfakeplayer automatically generates a nickname and spawns a player in using that nickname");
        }

        return true;
    }
}
