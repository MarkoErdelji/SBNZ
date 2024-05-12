package com.ftn.sbnz.controller;

import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.Achievement;
import com.ftn.sbnz.service.AchievementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/achievements")
public class AchievementController {
    private final AchievementServiceImpl achievementServiceImpl;

    @Autowired
    public AchievementController(AchievementServiceImpl achievementServiceImpl) {
        this.achievementServiceImpl = achievementServiceImpl;
    }

    @GetMapping
    public List<Achievement> getAllAchievements() {
        return achievementServiceImpl.getAllAchievements();
    }

    @GetMapping("/{id}")
    public Achievement getAchievementById(@PathVariable Long id) {
        return achievementServiceImpl.getAchievementById(id)
                .orElseThrow(() -> new NotFoundException("Achievement not found with id: " + id));
    }

    @PostMapping
    public Achievement createAchievement(@RequestBody Achievement achievement) {
        return achievementServiceImpl.saveAchievement(achievement);
    }

    @PutMapping("/{id}")
    public Achievement updateAchievement(@PathVariable Long id, @RequestBody Achievement achievement) {
        if (!achievementServiceImpl.getAchievementById(id).isPresent()) {
            throw new NotFoundException("Achievement not found with id: " + id);
        }

        achievement.setId(id); // Make sure the ID is set correctly
        return achievementServiceImpl.saveAchievement(achievement);
    }

    @DeleteMapping("/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        if (!achievementServiceImpl.getAchievementById(id).isPresent()) {
            throw new NotFoundException("Achievement not found with id: " + id);
        }

        achievementServiceImpl.deleteAchievementById(id);
    }
}
