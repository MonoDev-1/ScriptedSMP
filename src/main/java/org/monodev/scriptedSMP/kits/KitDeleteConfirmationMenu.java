package org.monodev.scriptedSMP.kits;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

public class KitDeleteConfirmationMenu implements InventoryHolder {

    private final KitData kitData;
    private final Inventory inventory;

    public KitDeleteConfirmationMenu(KitData kitData) {
        this.kitData = kitData;

        this.inventory = Bukkit.createInventory(
                this,
                27,
                Component.text("Confirm Kit Deletion")
        );

        ItemStack confirm = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.displayName(Component.text("Confirm Deletion"));
        confirm.setItemMeta(confirmMeta);

        ItemStack cancel = new ItemStack(Material.RED_CONCRETE);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.displayName(Component.text("Cancel"));
        cancel.setItemMeta(cancelMeta);

        inventory.setItem(11, confirm);
        inventory.setItem(15, cancel);

        ItemStack info = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.displayName(Component.text("Delete: " + kitData.name()));
        info.setItemMeta(infoMeta);

        inventory.setItem(13, info);
    }

    public KitData getKitData() {
        return kitData;
    }

    @Override
    public @NonNull Inventory getInventory() {
        return inventory;
    }
}