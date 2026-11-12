package org.monodev.scriptedSMP.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.monodev.scriptedSMP.kits.KitsMenu;

public class KitsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!sender.isOp()) {
            return false;
        }
        player.openInventory(new KitsMenu().getInventory());
        return true;
    }
}
