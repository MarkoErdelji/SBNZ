package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}