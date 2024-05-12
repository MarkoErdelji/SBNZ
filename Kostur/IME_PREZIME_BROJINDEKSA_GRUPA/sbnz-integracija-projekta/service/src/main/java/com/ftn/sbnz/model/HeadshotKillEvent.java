package com.ftn.sbnz.model;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class HeadshotKillEvent {

      private Long gameStatisticId;

    public HeadshotKillEvent() {
    }

    public HeadshotKillEvent(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

    public Long getGameStatisticId() {
        return gameStatisticId;
    }

    public void setGameStatisticId(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

}
