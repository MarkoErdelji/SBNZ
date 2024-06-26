package com.ftn.sbnz.service;

import com.ftn.sbnz.model.Achievement;
import com.ftn.sbnz.repository.AchievementRepository;
import com.ftn.sbnz.service.intefaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AchievementServiceImpl implements AchievementService {
    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    public Optional<Achievement> getAchievementById(Long id) {
        return achievementRepository.findById(id);
    }

    public Achievement saveAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    public void deleteAchievementById(Long id) {
        achievementRepository.deleteById(id);
    }
}
