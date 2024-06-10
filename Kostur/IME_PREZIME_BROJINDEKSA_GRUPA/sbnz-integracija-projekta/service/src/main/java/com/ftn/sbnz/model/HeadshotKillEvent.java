package com.ftn.sbnz.model;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class HeadshotKillEvent {

      private Long gameStatisticId;
    private Long userId;

    public HeadshotKillEvent() {
    }

    public HeadshotKillEvent(Long gameStatisticId,Long userId) {
        this.gameStatisticId = gameStatisticId;
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getGameStatisticId() {
        return gameStatisticId;
    }

    public void setGameStatisticId(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

}
