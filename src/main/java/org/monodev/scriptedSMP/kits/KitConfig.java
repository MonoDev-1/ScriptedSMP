package org.monodev.scriptedSMP.kits;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.monodev.scriptedSMP.ScriptedSMP;

import java.util.ArrayList;
import java.util.List;

public class KitConfig {
    private static ItemStack[] getItemArray(ConfigurationSection section, String key) {
        List<?> list = section.getList(key);

        if (list == null) {
            return new ItemStack[0];
        }

        ItemStack[] items = new ItemStack[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);

            if (item == null || item instanceof ItemStack) {
                items[i] = (ItemStack) item;
            } else {
                ScriptedSMP.instance.getLogger().warning(
                        "Invalid item in " + key + " at slot " + i
                );
            }
        }

        return items;
    }

    public static List<KitData> load() {
        ConfigurationSection section = ScriptedSMP.instance.getConfig().getConfigurationSection("kits");
        if (section == null) {
            ScriptedSMP.instance.getLogger().warning("Configuration section: kits dosen't exist. No kits were loaded.");
            section = ScriptedSMP.instance.getConfig().createSection("kits");
        }
        List<KitData> output = new ArrayList<>();
       for (String kitKey : section.getKeys(false)) {
           ConfigurationSection kitSection = section.getConfigurationSection(kitKey);

           int xp = kitSection.getInt("xp", 0);
           int hunger = kitSection.getInt("hunger", 20);
           double hitpoints = kitSection.getDouble("hitpoints", 20);

           ItemStack[] storage = getItemArray(kitSection, "storage");
           ItemStack[] armor = getItemArray(kitSection, "armor");
           ItemStack[] enderchest = getItemArray(kitSection, "enderchest");
           ItemStack offhand = kitSection.getItemStack("offhand");

           KitData kitData = new KitData(kitKey, storage, armor, enderchest, offhand, xp, hitpoints, hunger);
           output.add(kitData);
       }
        return output;
    }

    public static void saveKit(KitData kitData) {
        ConfigurationSection section = ScriptedSMP.instance.getConfig().getConfigurationSection("kits");
        if (section == null) {
            section = ScriptedSMP.instance.getConfig().createSection("kits");
        }
        ConfigurationSection kit = section.getConfigurationSection(kitData.name());
        if (kit == null) {
            kit = section.createSection(kitData.name());
        }
        kit.set("xp", kitData.xp());
        kit.set("hunger", kitData.hunger());
        kit.set("hitpoints", kitData.hitpoints());

        kit.set("storage", kitData.storage());
        kit.set("armor", kitData.armor());
        kit.set("enderchest", kitData.enderchest());
        kit.set("offhand", kitData.offhand());

        ScriptedSMP.instance.saveConfig();
    }

    public static void deleteKit(String name) {
        ConfigurationSection section = ScriptedSMP.instance.getConfig().getConfigurationSection("kits");
        if (section == null) {
            section = ScriptedSMP.instance.getConfig().createSection("kits");
        }

        section.set(name, null);

        ScriptedSMP.instance.saveConfig();
    }
}
