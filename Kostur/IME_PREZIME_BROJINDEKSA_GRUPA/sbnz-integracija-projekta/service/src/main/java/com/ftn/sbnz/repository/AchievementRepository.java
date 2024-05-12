package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
