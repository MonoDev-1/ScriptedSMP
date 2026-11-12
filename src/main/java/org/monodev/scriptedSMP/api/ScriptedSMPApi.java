package org.monodev.scriptedSMP.api;

import org.jetbrains.annotations.Nullable;
import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.api.config.APIConfigRegistry;
import org.monodev.scriptedSMP.api.event.EventManager;
import org.monodev.scriptedSMP.api.events.config.ConfigLoadedEvent;
import org.monodev.scriptedSMP.api.events.config.ConfigRegistryEvent;
import org.monodev.scriptedSMP.bots.FakePlayerManager;
import org.monodev.scriptedSMP.config.ConfigRegistry;
import org.monodev.scriptedSMP.voicechat.VoiceChatManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScriptedSMPApi implements ScriptedAPI {
    private final List<ScriptedPlugin> plugins = new CopyOnWriteArrayList<>();
    private final ScriptedEventManager scriptedEventManager;

    public ScriptedSMPApi() {
        scriptedEventManager = new ScriptedEventManager();
    }

    @Override
    public FakePlayerManager getFakePlayerManager() {
        return ScriptedSMP.instance.getFakePlayerManager();
    }

    @Override
    public EventManager events() {
        return scriptedEventManager;
    }

    @Override
    public @Nullable VoiceChatManager getVoiceChatManager() {
        return ScriptedSMP.instance.getVoiceChatManager();
    }

    @Override
    public void registerPlugin(ScriptedPlugin plugin) {
        plugins.add(plugin);
        plugin.registerEvents(scriptedEventManager);
    }

    /**
     * <p>Called by ScriptedSMP when the server loads to fire config registry event and load the config.</p>
     * @apiNote This is a private method. Do not call this method or you may break things
     */
    public void onServerLoad() {
        scriptedEventManager.publish(new ConfigRegistryEvent(this, new APIConfigRegistry()));
        ConfigRegistry.load(ScriptedSMP.instance);
        scriptedEventManager.publish(new ConfigLoadedEvent(this));
    }
}
