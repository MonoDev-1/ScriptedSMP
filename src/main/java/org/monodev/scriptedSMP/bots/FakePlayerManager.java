package org.monodev.scriptedSMP.bots;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;
import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.api.ScriptedSMPApi;
import org.monodev.scriptedSMP.api.events.fakeplayer.FakePlayerLeaveEvent;
import org.monodev.scriptedSMP.api.events.fakeplayer.FakePlayerSpawnEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakePlayerManager {
    private final Map<UUID, FakePlayer> activeBots = new HashMap<>();
    @Nullable
    public  FakePlayer spawnFakePlayer(String name, Location location) {
        UUID uuid = UUID.randomUUID();
        return spawnFakePlayer(uuid, name, location);
    }

    @Nullable
    public FakePlayer spawnFakePlayer(UUID uuid, String name, Location location) {
        if (activeBots.containsKey(uuid)) {
            return activeBots.get(uuid);
        }


        FakePlayer fakePlayer = new FakePlayer(uuid, name, location);
        FakePlayerSpawnEvent spawnEvent = new FakePlayerSpawnEvent(ScriptedSMP.instance.getApi(), fakePlayer);
        ScriptedSMP.instance.getApi().events().publish(spawnEvent);
        if (spawnEvent.isCancelled()) return null;
        fakePlayer.join();
        activeBots.put(uuid, fakePlayer);

        return fakePlayer;
    }

    @Nullable
    public FakePlayer spawnFakePlayer(UUID uuid, Location location) {
        return this.spawnFakePlayer(uuid, NicknameGenerator.genNickname(), location);
    }

    @Nullable
    public FakePlayer spawnFakePlayer(Location location) {
        return this.spawnFakePlayer(UUID.randomUUID(), NicknameGenerator.genNickname(), location);
    }

    public void removeFakePlayer(UUID uuid, FakePlayerLeaveEvent.Cause cause) {
        FakePlayer fakePlayer = activeBots.remove(uuid);
        if (fakePlayer != null) {
            fakePlayer.leave();

            ScriptedSMPApi api = ScriptedSMP.instance.getApi();

            api.events().publish(
                    new FakePlayerLeaveEvent(api, fakePlayer, cause)
            );
        }
    }

    public void removeFakePlayer(UUID uuid) {
        removeFakePlayer(uuid, FakePlayerLeaveEvent.Cause.PLUGIN);
    }

    @Nullable
    public FakePlayer getFakePlayer(UUID uuid) {
        return activeBots.get(uuid);
    }

    public Map<UUID, FakePlayer> getActiveBots() {
        return Collections.unmodifiableMap(activeBots);
    }

    public void removeAll() {
        for (FakePlayer bot : activeBots.values()) {
            bot.leave();
            ScriptedSMPApi api = ScriptedSMP.instance.getApi();

            api.events().publish(
                    new FakePlayerLeaveEvent(api, bot, FakePlayerLeaveEvent.Cause.SCRIPTEDSMP)
            );
        }
        activeBots.clear();
    }
}