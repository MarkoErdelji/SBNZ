package com.ftn.sbnz.model;

public class UserProcessed {
    private Long userId;
    private Long statisticId;
    public UserProcessed() {
    }

    public UserProcessed(Long userId,Long statisticId) {
        this.userId = userId;
        this.statisticId = statisticId;
    }

    public Long getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(Long statisticId) {
        this.statisticId = statisticId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}