package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.GameStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameStatisticRepository extends JpaRepository<GameStatistic, Long> {
        List<GameStatistic> findByUserId(Long userId);
}
