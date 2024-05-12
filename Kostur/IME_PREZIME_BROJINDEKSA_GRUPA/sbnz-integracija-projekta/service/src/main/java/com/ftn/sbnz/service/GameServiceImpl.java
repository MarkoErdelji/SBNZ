package com.ftn.sbnz.service;

import com.ftn.sbnz.model.Game;
import com.ftn.sbnz.repository.GameRepository;
import com.ftn.sbnz.service.intefaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }
}
