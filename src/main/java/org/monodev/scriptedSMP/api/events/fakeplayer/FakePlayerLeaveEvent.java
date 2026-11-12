package org.monodev.scriptedSMP.api.events.fakeplayer;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseEvent;
import org.monodev.scriptedSMP.bots.FakePlayer;
import org.monodev.scriptedSMP.bots.FakePlayerManager;

public class FakePlayerLeaveEvent extends BaseEvent {
    private final FakePlayer fakePlayer;
    private final Cause cause;

    public FakePlayerLeaveEvent(ScriptedAPI api, FakePlayer fakePlayer, Cause cause) {
        super(api);
        this.fakePlayer = fakePlayer;
        this.cause = cause;
    }

    public FakePlayer getFakePlayer() {
        return fakePlayer;
    }

    public Cause getCause() {
        return cause;
    }

    /**
     * <p>Cause can be disconnected even if caused by a plugin if it does not use the manager to remove the fake player.</p>
     * <p>If the cause is {@code SCRIPTEDSMP} then that means the fake player left because of /scriptedremovefakeplayer <b>OR</b> because something called {@link FakePlayerManager}.removeAll();</p>
     * @author MonoDev
     * @since 1.0
     * @version 1.0
     * @see FakePlayer
     * @see FakePlayerManager
     */
    public enum Cause {DEATH, DISCONNECTED, PLUGIN, SCRIPTEDSMP}
}
