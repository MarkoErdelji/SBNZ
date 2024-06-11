package com.ftn.sbnz.controller;

import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.GameStatistic;
import com.ftn.sbnz.service.GameStatisticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game-statistics")
public class GameStatisticController {
    private final GameStatisticServiceImpl gameStatisticService;

    @Autowired
    public GameStatisticController(GameStatisticServiceImpl gameStatisticService) {
        this.gameStatisticService = gameStatisticService;
    }

    @GetMapping
    public List<GameStatistic> getAllGameStatistics() {
        return gameStatisticService.getAllGameStatistics();
    }

    @GetMapping("/{id}")
    public GameStatistic getGameStatisticById(@PathVariable Long id) {
        return gameStatisticService.getGameStatisticById(id)
                .orElseThrow(() -> new NotFoundException("GameStatistic not found with id: " + id));
    }

    @PostMapping
    public GameStatistic createGameStatistic(@RequestBody GameStatistic gameStatistic) {
        return gameStatisticService.saveGameStatistic(gameStatistic);
    }

    @PutMapping("/{id}")
    public GameStatistic updateGameStatistic(@PathVariable Long id, @RequestBody GameStatistic gameStatistic) {
        if (!gameStatisticService.getGameStatisticById(id).isPresent()) {
            throw new NotFoundException("GameStatistic not found with id: " + id);
        }

        gameStatistic.setId(id); // Make sure the ID is set correctly
        return gameStatisticService.saveGameStatistic(gameStatistic);
    }

    @DeleteMapping("/{id}")
    public void deleteGameStatistic(@PathVariable Long id) {
        if (!gameStatisticService.getGameStatisticById(id).isPresent()) {
            throw new NotFoundException("GameStatistic not found with id: " + id);
        }

        gameStatisticService.deleteGameStatisticById(id);
    }
}
