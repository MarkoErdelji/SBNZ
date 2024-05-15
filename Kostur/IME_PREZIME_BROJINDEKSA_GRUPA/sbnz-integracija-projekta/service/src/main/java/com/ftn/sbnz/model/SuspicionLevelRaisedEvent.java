package com.ftn.sbnz.model;

public class SuspicionLevelRaisedEvent {
    private long userId;

    public SuspicionLevelRaisedEvent(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
