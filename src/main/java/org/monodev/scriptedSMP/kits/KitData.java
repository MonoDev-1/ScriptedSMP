package org.monodev.scriptedSMP.kits;

import org.bukkit.inventory.ItemStack;

public record KitData(
        String name,
        ItemStack[] storage,
        ItemStack[] armor,
        ItemStack[] enderchest,
        ItemStack offhand,
        int xp,
        double hitpoints,
        int hunger) {
}
