package com.ftn.sbnz.service;

import com.ftn.sbnz.model.GameStatistic;
import com.ftn.sbnz.repository.GameStatisticRepository;
import com.ftn.sbnz.service.intefaces.GameStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameStatisticServiceImpl implements GameStatisticService {
    private final GameStatisticRepository gameStatisticRepository;

    @Autowired
    public GameStatisticServiceImpl(GameStatisticRepository gameStatisticRepository) {
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
