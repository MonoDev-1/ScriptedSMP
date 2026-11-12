package org.monodev.scriptedSMP.kits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.AnvilView;
import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.api.ScriptedSMPApi;
import org.monodev.scriptedSMP.api.events.kits.KitCreateEvent;
import org.monodev.scriptedSMP.api.events.kits.KitGiveEvent;
import org.monodev.scriptedSMP.api.events.kits.KitGivenEvent;

import java.util.List;
//TODO: add kit deletion
public class KitMenuListener implements Listener {
    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof KitsMenu kitsMenu) {
            event.setCancelled(true);
            if (event.getClickedInventory() == null || event.getClickedInventory() != event.getInventory()) {
                return;
            }

            int slot = event.getSlot();
            switch (slot) {
                case 45 -> kitsMenu.previousPage();
                case 48 -> kitsMenu.reloadKits();
                case 53 -> kitsMenu.nextPage();
                case 50 -> openKitNamingAnvil((Player) event.getWhoClicked());
                default -> {
                    if (slot >= 0 && slot < 45) {
                        int kitIndex = (kitsMenu.getPage() - 1) * 45 + slot;
                        handleKitClick((Player) event.getWhoClicked(), kitIndex);
                    }
                }
            }
        }
    }

    private void openKitNamingAnvil(Player player) {
        player.openAnvil(player.getLocation(), true);

        if (player.getOpenInventory().getTopInventory() instanceof AnvilInventory anvil) {
            anvil.setFirstItem(new ItemStack(Material.PAPER));
        }
    }

    @EventHandler
    public void onAnvilClick(InventoryClickEvent event) {
        if (!(event.getView() instanceof AnvilView anvilView)) return;
        if (event.getRawSlot() != 2) return;

        Player player = (Player) event.getWhoClicked();

        String kitName = anvilView.getRenameText();

        if (kitName == null || kitName.isBlank()) {
            event.setCancelled(true);
            player.sendMessage(Component.text("Invalid kit name!"));
            return;
        }

        event.setCancelled(true);

        PlayerInventory inventory = player.getInventory();

        KitData newKit = new KitData(
                kitName.trim(),
                inventory.getStorageContents(),
                inventory.getArmorContents(),
                player.getEnderChest().getContents(),
                inventory.getItemInOffHand(),
                player.getTotalExperience(),
                player.getHealth(),
                player.getFoodLevel()
        );

        ScriptedSMPApi api = ScriptedSMP.instance.getApi();
        KitCreateEvent kitCreateEvent = new KitCreateEvent(api, newKit, player);
        api.events().publish(kitCreateEvent);

        if (kitCreateEvent.isCancelled()) {
            player.sendMessage("The creation of this kit was cancelled by an external plugin.");
            player.closeInventory();
        }

        KitConfig.saveKit(newKit);

        player.closeInventory();
        player.sendMessage(Component.text("Kit '" + kitName + "' successfully saved!"));
        player.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW);
        player.openInventory(new KitsMenu().getInventory());
    }

    private void handleKitClick(Player player, int kitIndex) {
        List<KitData> kitDataList = KitConfig.load();
        KitData[] kitDataArray = kitDataList.toArray(new KitData[0]);

        try {
            KitData kitData = kitDataArray[kitIndex];
            ScriptedSMPApi api = ScriptedSMP.instance.getApi();
            KitGiveEvent giveEvent = new KitGiveEvent(api, kitData, player);
            api.events().publish(giveEvent);
            if (giveEvent.isCancelled()) {
                player.sendMessage("Failed to equip kit. A external plugin cancelled this event.");
                return;
            }

            PlayerInventory inventory = player.getInventory();

            inventory.setStorageContents(kitData.storage());
            inventory.setArmorContents(kitData.armor());
            inventory.setItemInOffHand(kitData.offhand());
            player.getEnderChest().setStorageContents(kitData.enderchest());
            player.setTotalExperience(kitData.xp());
            player.setHealth(kitData.hitpoints());
            player.setFoodLevel(kitData.hunger());

            //TODO: Save saturation
            api.events().publish(new KitGivenEvent(api, kitData, player));

            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1, 1);
            player.sendMessage("Equipped kit: " + kitData.name());
        } catch (Exception e) {
            player.sendMessage("Failed to equip kit. Kit may have been deleted? Try refreshing");
        }
    }
}
