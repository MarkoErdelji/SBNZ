package com.ftn.sbnz.service.intefaces;

import com.ftn.sbnz.model.GameStatistic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameStatisticService {
    List<GameStatistic> getAllGameStatistics();
    Optional<GameStatistic> getGameStatisticById(Long id);
    GameStatistic saveGameStatistic(GameStatistic gameStatistic);
    void deleteGameStatisticById(Long id);
}
