package org.monodev.scriptedSMP.api.events.nicknames;

import org.bukkit.entity.Player;
import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseCancellableEvent;

public class NicknameSetEvent extends BaseCancellableEvent {
    private final Player player;
    private final Player issuer;
    private final String nickname;
    public NicknameSetEvent(ScriptedAPI api, Player player, Player issuer, String nickname) {
        super(api);
        this.player = player;
        this.issuer = issuer;
        this.nickname = nickname;
    }

    /**
     * @author MonoDev
     * @return The player who is receiving the new nickname.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * issuer: Player responsible for triggering this event
     * @return The issuer who is trying to set the other player's nickname
     * @author MonoDev
     */
    public Player getIssuer() {
        return issuer;
    }

    /**
     * @author MonoDev
     * @return the nickname that the player will receive
     */
    public String getNickname() {
        return nickname;
    }
}
