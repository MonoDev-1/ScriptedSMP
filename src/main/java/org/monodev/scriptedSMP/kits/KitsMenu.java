package org.monodev.scriptedSMP.kits;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KitsMenu implements InventoryHolder {
    private static final int INVENTORY_SIZE = 54;
    private static final int ITEMS_PER_PAGE = 45;

    private final Inventory inventory;
    private List<KitData> kitDataList;
    private int page = 1;

    public KitsMenu() {
        this.inventory = Bukkit.createInventory(this, INVENTORY_SIZE, Component.text("Kits"));
        this.kitDataList = KitConfig.load();
        refreshMenu();
    }

    public void refreshMenu() {
        this.inventory.clear();
        putItems();
        putControls();
    }

    private void putItems() {
        if (kitDataList == null || kitDataList.isEmpty()) {
            return;
        }

        int startIndex = (page - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, kitDataList.size());

        int slot = 0;
        for (int i = startIndex; i < endIndex; i++) {
            KitData data = kitDataList.get(i);

            ItemStack kit = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = kit.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text(data.name()));
                kit.setItemMeta(itemMeta);
            }

            this.inventory.setItem(slot, kit);
            slot++;
        }
    }

    private void putControls() {
        int maxPages = getMaxPages();

        // Previous Page Button (Slot 45)
        if (page > 1) {
            this.inventory.setItem(45, createGuiItem(Material.ARROW, "Previous Page"));
        }

        // Refresh Button (Slot 48)
        this.inventory.setItem(48, createGuiItem(Material.SUNFLOWER, "Refresh"));

        // Page Indicator (Slot 49)
        this.inventory.setItem(49, createGuiItem(Material.WRITTEN_BOOK, "Page " + page + " / " + maxPages));

        this.inventory.setItem(50, createGuiItem(Material.ANVIL, "§aSave Kit"));

        // Next Page Button (Slot 53)
        if (page < maxPages) {
            this.inventory.setItem(53, createGuiItem(Material.ARROW, "Next Page"));
        }
    }

    private ItemStack createGuiItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(name));
            item.setItemMeta(meta);
        }
        return item;
    }

    public int getMaxPages() {
        if (kitDataList == null || kitDataList.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) kitDataList.size() / ITEMS_PER_PAGE);
    }

    public void nextPage() {
        if (page < getMaxPages()) {
            page++;
            refreshMenu();
        }
    }

    public void previousPage() {
        if (page > 1) {
            page--;
            refreshMenu();
        }
    }

    public void reloadKits() {
        this.kitDataList = KitConfig.load();
        refreshMenu();
    }

    public int getPage() {
        return page;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}