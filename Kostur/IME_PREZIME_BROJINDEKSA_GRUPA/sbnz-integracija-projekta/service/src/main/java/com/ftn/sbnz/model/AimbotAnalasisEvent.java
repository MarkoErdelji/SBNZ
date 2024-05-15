package com.ftn.sbnz.model;

public class AimbotAnalasisEvent {
    private Long gameStatisticId;

    public AimbotAnalasisEvent(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

    public Long getGameStatisticId() {
        return gameStatisticId;
    }

    public void setGameStatisticId(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }
}
