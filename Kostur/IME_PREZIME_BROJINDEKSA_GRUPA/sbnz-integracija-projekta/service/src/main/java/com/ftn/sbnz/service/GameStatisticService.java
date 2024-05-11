package com.ftn.sbnz.service;

import com.ftn.sbnz.model.GameStatistic;
import com.ftn.sbnz.repository.GameStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameStatisticService {
    private final GameStatisticRepository gameStatisticRepository;

    @Autowired
    public GameStatisticService(GameStatisticRepository gameStatisticRepository) {
        this.gameStatisticRepository = gameStatisticRepository;
    }

    public List<GameStatistic> getAllGameStatistics() {
        return gameStatisticRepository.findAll();
    }

    public Optional<GameStatistic> getGameStatisticById(Long id) {
        return gameStatisticRepository.findById(id);
    }

    public GameStatistic saveGameStatistic(GameStatistic gameStatistic) {
        return gameStatisticRepository.save(gameStatistic);
    }

    public void deleteGameStatisticById(Long id) {
        gameStatisticRepository.deleteById(id);
    }
}
