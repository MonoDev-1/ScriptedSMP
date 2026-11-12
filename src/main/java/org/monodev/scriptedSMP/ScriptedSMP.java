package org.monodev.scriptedSMP;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.ScriptedSMPApi;
import org.monodev.scriptedSMP.api.events.fakeplayer.FakePlayerLeaveEvent;
import org.monodev.scriptedSMP.bots.FakePlayer;
import org.monodev.scriptedSMP.bots.FakePlayerManager;
import org.monodev.scriptedSMP.cmds.*;
import org.monodev.scriptedSMP.config.Config;
import org.monodev.scriptedSMP.config.ConfigRegistry;
import org.monodev.scriptedSMP.kits.KitMenuListener;
import org.monodev.scriptedSMP.listeners.ConfigEnforcerListener;
import org.monodev.scriptedSMP.voicechat.VoiceChatManager;

//TODO: BOTS, TELEPORTS

public final class ScriptedSMP extends JavaPlugin implements Listener {
    public static ScriptedSMP instance;
    private VoiceChatManager voiceChatManager = null;
    private FakePlayerManager fakePlayerManager = null;
    private boolean serverLoaded = false;

    private ScriptedSMPApi api;

    @EventHandler
    private void onServerLoad(ServerLoadEvent event) {
        serverLoaded = true;
        api.onServerLoad();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        FakePlayer fakePlayer = fakePlayerManager.getFakePlayer(event.getPlayer().getUniqueId());

        if (fakePlayer == null) return;
        fakePlayerManager.removeFakePlayer(event.getPlayer().getUniqueId(), FakePlayerLeaveEvent.Cause.DISCONNECTED);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        api = new ScriptedSMPApi();
        Config.toLoadClass();


        fakePlayerManager = new FakePlayerManager();

        getCommand("scriptedconfig").setExecutor(new ConfigEditCommand());
        getCommand("scriptedspawnfakeplayer").setExecutor(new SpawnFakePlayer());
        getCommand("scriptedremovefakeplayer").setExecutor(new RemoveFakePlayer());
        getCommand("scriptedkits").setExecutor(new KitsCommand());
        getCommand("scriptedgeneratenickname").setExecutor(new NicknameGenerateCommand());
        getServer().getPluginManager().registerEvents(new ConfigEnforcerListener(), this);
        getServer().getPluginManager().registerEvents(new KitMenuListener(), this);
        getServer().getPluginManager().registerEvents(this, this);

        if (Bukkit.getPluginManager().getPlugin("voicechat") != null) {
            voiceChatManager = new VoiceChatManager(this);
            voiceChatManager.initialize();
            getLogger().info("Voice chat capabilities are now enabled.");
        } else {
            getLogger().warning("Simple Voice Chat plugin not installed. Skipping integration.");
        }

        Bukkit.getServer().getServicesManager().register(
                ScriptedAPI.class,
                api,
                this,
                ServicePriority.Normal
        );
        getLogger().info("Registered ScriptedSMP Api and finished enabling.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (!serverLoaded) {
            return;
        }
        ConfigRegistry.save(this);
        fakePlayerManager.removeAll();
        saveConfig();
    }

    public FakePlayerManager getFakePlayerManager() {
        return fakePlayerManager;
    }
    public ScriptedSMPApi getApi() {return this.api;}

    public VoiceChatManager getVoiceChatManager() {
        return voiceChatManager;
    }
}
