package org.monodev.scriptedSMP.voicechat;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.CreateGroupEvent;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.monodev.scriptedSMP.config.Config;

public final class VoiceChatManager {

    private final JavaPlugin plugin;

    private boolean available;
    private VoicechatServerApi api;

    public VoiceChatManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        BukkitVoicechatService service = null;
        try {
             service = Bukkit.getServicesManager()
                    .load(BukkitVoicechatService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (service == null) {
            plugin.getLogger().warning(
                    "Simple Voice Chat not found! Voice integration disabled."
            );
            return;
        }

        service.registerPlugin(new ScriptedVoicechatPlugin(this));

        available = true;

        plugin.getLogger().info(
                "Simple Voice Chat integration registered!"
        );
    }

    public void onCreateGroup(CreateGroupEvent event) {
        Player player = (Player) event.getConnection().getPlayer().getPlayer();
        if (player.hasPermission("scriptedsmp.config.bypass_voice_restrictions")) {
            return;
        }

        if (!Config.CREATE_GROUPS.value()) {
            event.cancel();
            return;
        }

        if (!Config.PRIVATE_GROUPS_ALLOWED.value()) {
            if (event.getGroup().hasPassword()) {
                event.cancel();
            }
        }

    }

    public void onMicrophonePacket(MicrophonePacketEvent event) {
        Player player = (Player) event.getSenderConnection().getPlayer().getPlayer();
        if (player.hasPermission("scriptedsmp.config.bypass_voice_restrictions")) {
            return;
        }

        if (!Config.VOICE_ALLOWED.value()) {
            event.cancel();
        }
    }

    public void onVoicechatServerStarted(VoicechatServerStartedEvent event) {
        api = event.getVoicechat();
    }

    public boolean isAvailable() {
        return available;
    }

    public void shutdown() {
        available = false;
    }
}