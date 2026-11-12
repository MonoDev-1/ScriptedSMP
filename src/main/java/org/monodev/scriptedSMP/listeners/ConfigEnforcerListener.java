package org.monodev.scriptedSMP.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.api.events.fakeplayer.FakePlayerLeaveEvent;
import org.monodev.scriptedSMP.bots.FakePlayerManager;
import org.monodev.scriptedSMP.config.Config;

import java.util.Date;

public class ConfigEnforcerListener implements Listener {
    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        if (!Config.PLACE_BLOCKS.value()) {
            if (!event.getPlayer().hasPermission("scriptedsmp.config.bypass_block_place")) {
                event.getPlayer().sendActionBar(Component.text("Block Placing is disabled."));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBlockDestroy(BlockBreakEvent event) {
        if (!Config.BREAK_BLOCKS.value()) {
            if (!event.getPlayer().hasPermission("scriptedsmp.config.bypass_block_break")) {
                event.getPlayer().sendActionBar(Component.text("Block Breaking is disabled."));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onInteractBlock(PlayerInteractEvent event) {
        if (event.hasBlock() && !Config.INTERACT_BLOCKS.value()) {
            if (!event.getPlayer().hasPermission("scriptedsmp.config.bypass_interact_block")) {
                event.getPlayer().sendActionBar(Component.text("Block Interaction is disabled."));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        if (ScriptedSMP.instance.getFakePlayerManager().getFakePlayer(event.getPlayer().getUniqueId()) != null) {
            ScriptedSMP.instance.getFakePlayerManager().removeFakePlayer(event.getPlayer().getUniqueId(), FakePlayerLeaveEvent.Cause.DEATH);
            if (Config.DEATH_SOUND.value()) {
                event.getPlayer().getWorld().playSound(
                        event.getPlayer().getLocation(),
                        Sound.ENTITY_WITHER_SPAWN,
                        SoundCategory.PLAYERS,
                        1, 1
                );
            }
            return;
        }

        if (event.getPlayer().hasPermission("scriptedsmp.config.ignore_death")) return;
        if (Config.DEATH_SOUND.value()) {
            event.getPlayer().getWorld().playSound(
                    event.getPlayer().getLocation(),
                    Sound.ENTITY_WITHER_SPAWN,
                    SoundCategory.PLAYERS,
                    1, 1
            );
        }

        boolean banned = false;

        if (Config.PERMA_DEATH.value()) {
            event.getPlayer().ban("You died. Thank you for playing! Contact an admin if you believe this is an error.",
                    (Date) null, "ScriptedSMP", true);
            banned = true;
        }

        if (Config.KICK_ON_DEATH.value() && !banned) {
            event.getPlayer().kick(Component.text("You died. Thank you for playing!"), PlayerKickEvent.Cause.PLUGIN);
        }
    }
}
