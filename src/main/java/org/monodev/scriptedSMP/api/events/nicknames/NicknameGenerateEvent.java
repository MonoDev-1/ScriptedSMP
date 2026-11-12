package org.monodev.scriptedSMP.api.events.nicknames;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseEvent;

public class NicknameGenerateEvent extends BaseEvent {
    private final String originalNickname;
    private String nickname;
    public NicknameGenerateEvent(ScriptedAPI api, String nickname) {
        super(api);
        this.nickname = nickname;
        this.originalNickname = nickname;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOriginalNickname() {
        return originalNickname;
    }
}
