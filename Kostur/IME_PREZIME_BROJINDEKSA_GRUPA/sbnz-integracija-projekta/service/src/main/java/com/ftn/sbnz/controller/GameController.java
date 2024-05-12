package com.ftn.sbnz.controller;

import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.Game;
import com.ftn.sbnz.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameServiceImpl gameServiceImpl;

    @Autowired
    public GameController(GameServiceImpl gameServiceImpl) {
        this.gameServiceImpl = gameServiceImpl;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameServiceImpl.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameServiceImpl.getGameById(id)
                .orElseThrow(() -> new NotFoundException("Game not found with id: " + id));
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameServiceImpl.saveGame(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        if (!gameServiceImpl.getGameById(id).isPresent()) {
            throw new NotFoundException("Game not found with id: " + id);
        }

        game.setId(id); // Make sure the ID is set correctly
        return gameServiceImpl.saveGame(game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        if (!gameServiceImpl.getGameById(id).isPresent()) {
            throw new NotFoundException("Game not found with id: " + id);
        }

        gameServiceImpl.deleteGameById(id);
    }
}
