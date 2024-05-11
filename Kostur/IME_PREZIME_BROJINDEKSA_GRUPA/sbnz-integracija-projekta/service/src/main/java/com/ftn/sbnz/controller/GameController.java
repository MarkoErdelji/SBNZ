package com.ftn.sbnz.controller;

import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.Game;
import com.ftn.sbnz.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameService.getGameById(id)
                .orElseThrow(() -> new NotFoundException("Game not found with id: " + id));
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameService.saveGame(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        if (!gameService.getGameById(id).isPresent()) {
            throw new NotFoundException("Game not found with id: " + id);
        }

        game.setId(id); // Make sure the ID is set correctly
        return gameService.saveGame(game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        if (!gameService.getGameById(id).isPresent()) {
            throw new NotFoundException("Game not found with id: " + id);
        }

        gameService.deleteGameById(id);
    }
}
