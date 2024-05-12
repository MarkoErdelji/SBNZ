package com.ftn.sbnz.service.intefaces;

import com.ftn.sbnz.model.Achievement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AchievementService {
    List<Achievement> getAllAchievements();

    Optional<Achievement> getAchievementById(Long id);

    Achievement saveAchievement(Achievement achievement);

    void deleteAchievementById(Long id);
}
