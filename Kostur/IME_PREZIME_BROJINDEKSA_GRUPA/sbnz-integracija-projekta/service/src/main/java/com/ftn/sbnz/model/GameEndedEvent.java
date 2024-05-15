package com.ftn.sbnz.model;

public class GameEndedEvent {
    private Long gameId;

    public GameEndedEvent(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
