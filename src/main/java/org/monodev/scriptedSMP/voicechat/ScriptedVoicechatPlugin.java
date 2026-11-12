package org.monodev.scriptedSMP.voicechat;

import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.*;

public final class ScriptedVoicechatPlugin implements VoicechatPlugin {

    private final VoiceChatManager manager;

    public ScriptedVoicechatPlugin(VoiceChatManager manager) {
        this.manager = manager;
    }

    @Override
    public String getPluginId() {
        return "scriptedsmp";
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(
                CreateGroupEvent.class,
                manager::onCreateGroup
        );

        registration.registerEvent(
                MicrophonePacketEvent.class,
                manager::onMicrophonePacket
        );

        registration.registerEvent(
                VoicechatServerStartedEvent.class,
                manager::onVoicechatServerStarted
        );
    }
}