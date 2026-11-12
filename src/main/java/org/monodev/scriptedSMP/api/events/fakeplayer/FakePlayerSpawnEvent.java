package org.monodev.scriptedSMP.api.events.fakeplayer;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseCancellableEvent;
import org.monodev.scriptedSMP.bots.FakePlayer;

public class FakePlayerSpawnEvent extends BaseCancellableEvent {
    private final FakePlayer player;

    public FakePlayerSpawnEvent(ScriptedAPI api, FakePlayer fakePlayer) {
        super(api);
        this.player = fakePlayer;
    }

    public FakePlayer getFakePlayer() {
        return player;
    }
}