package com.ftn.sbnz.model;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class RegularKillEvent {
     private Long gameStatisticId;
     private Long userId;
    private long timestamp;

    public RegularKillEvent() {
    }

    public RegularKillEvent(Long gameStatisticId, long timestamp,Long userId) {
        this.gameStatisticId = gameStatisticId;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public Long getGameStatisticId() {
        return gameStatisticId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
