package com.ftn.sbnz.service.intefaces;

import com.ftn.sbnz.dto.UserGameDTO;
import com.ftn.sbnz.model.Game;
import com.ftn.sbnz.model.enums.Action;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameService {
    List<Game> getAllGames();

    Optional<Game> getGameById(Long id);
    void processGameAction(Long gameId, Action action, String username);
     Game saveGame(List<UserGameDTO> userDTOs);
    void deleteGameById(Long id);
    void endGame(Long gameId);
}
