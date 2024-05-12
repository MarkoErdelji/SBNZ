package com.ftn.sbnz.model;


import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class UtilityUsageEvent {
       private Long gameStatisticId;
    private long timestamp;

    public UtilityUsageEvent() {
    }

    public UtilityUsageEvent(Long gameStatisticId, long timestamp) {
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
