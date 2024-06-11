package com.ftn.sbnz.model;

public class WallhackAnalasisEvent {
    private Long gameStatisticId;

    public WallhackAnalasisEvent(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

       public Long getGameStatisticId() {
        return gameStatisticId;
    }

    public void setGameStatisticId(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }
}
