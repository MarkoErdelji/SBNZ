package com.ftn.sbnz.model;

import java.io.Serializable;
import java.util.UUID;

public class AchievementCooldown implements Serializable {
    private Long userId;
    private Long gameStatisticId;
    private String achievementName;

    public AchievementCooldown(Long userId, Long gameStatisticId, String achievementName) {
        this.userId = userId;
        this.gameStatisticId = gameStatisticId;
        this.achievementName = achievementName;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGameStatisticId() {
        return gameStatisticId;
    }

    public void setGameStatisticId(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }
}
