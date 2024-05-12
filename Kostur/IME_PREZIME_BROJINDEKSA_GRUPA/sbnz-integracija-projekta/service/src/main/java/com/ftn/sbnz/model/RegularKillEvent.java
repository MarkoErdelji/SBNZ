package com.ftn.sbnz.model;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class RegularKillEvent {
     private Long gameStatisticId;
    private long timestamp;

    public RegularKillEvent() {
    }

    public RegularKillEvent(Long gameStatisticId, long timestamp) {
        this.gameStatisticId = gameStatisticId;
        this.timestamp = timestamp;
    }

    public Long getGameStatisticId() {
        return gameStatisticId;
    }

    public void setGameStatisticId(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
