package com.ftn.sbnz.service.intefaces;

import com.ftn.sbnz.model.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameService {
    List<Game> getAllGames();

    Optional<Game> getGameById(Long id);

    Game saveGame(Game game);

    void deleteGameById(Long id);
}
