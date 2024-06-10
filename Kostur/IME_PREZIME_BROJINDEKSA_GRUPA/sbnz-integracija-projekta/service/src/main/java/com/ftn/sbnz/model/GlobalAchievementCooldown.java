package com.ftn.sbnz.model;

public class GlobalAchievementCooldown {
    private Long userId;
    private String achievementName;
    private long timestamp;

    public GlobalAchievementCooldown(Long userId, String achievementName) {
        this.userId = userId;
        this.achievementName = achievementName;
        this.timestamp = System.currentTimeMillis();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

