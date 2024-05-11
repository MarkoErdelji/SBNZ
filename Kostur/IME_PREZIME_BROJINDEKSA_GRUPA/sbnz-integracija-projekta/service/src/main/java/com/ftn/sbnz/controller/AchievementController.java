package com.ftn.sbnz.controller;

import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.Achievement;
import com.ftn.sbnz.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {
    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public List<Achievement> getAllAchievements() {
        return achievementService.getAllAchievements();
    }

    @GetMapping("/{id}")
    public Achievement getAchievementById(@PathVariable Long id) {
        return achievementService.getAchievementById(id)
                .orElseThrow(() -> new NotFoundException("Achievement not found with id: " + id));
    }

    @PostMapping
    public Achievement createAchievement(@RequestBody Achievement achievement) {
        return achievementService.saveAchievement(achievement);
    }

    @PutMapping("/{id}")
    public Achievement updateAchievement(@PathVariable Long id, @RequestBody Achievement achievement) {
        if (!achievementService.getAchievementById(id).isPresent()) {
            throw new NotFoundException("Achievement not found with id: " + id);
        }

        achievement.setId(id); // Make sure the ID is set correctly
        return achievementService.saveAchievement(achievement);
    }

    @DeleteMapping("/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        if (!achievementService.getAchievementById(id).isPresent()) {
            throw new NotFoundException("Achievement not found with id: " + id);
        }

        achievementService.deleteAchievementById(id);
    }
}
