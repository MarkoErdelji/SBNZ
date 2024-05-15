package com.ftn.sbnz.controller;

import com.ftn.sbnz.dto.UserGameDTO;
import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.Game;
import com.ftn.sbnz.model.GameStatistic;
import com.ftn.sbnz.model.TokenPrincipalModel;
import com.ftn.sbnz.model.User;
import com.ftn.sbnz.model.enums.Action;
import com.ftn.sbnz.model.enums.Report;
import com.ftn.sbnz.service.GameServiceImpl;
import com.ftn.sbnz.service.intefaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameServiceImpl gameServiceImpl;
    @Autowired
    private UserService userService;
    @Autowired
    public GameController(GameServiceImpl gameServiceImpl) {
        this.gameServiceImpl = gameServiceImpl;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameServiceImpl.getAllGames();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Game getGameById(@PathVariable Long id) {
        return gameServiceImpl.getGameById(id)
                .orElseThrow(() -> new NotFoundException("Game not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createGame(@RequestBody List<UserGameDTO> userDTOs) {

        return new ResponseEntity<>(gameServiceImpl.saveGame(userDTOs), HttpStatus.OK);
    }

   @PostMapping("/{gameId}")
   @PreAuthorize("hasAuthority('USER')")
    public void performAction(
            @PathVariable Long gameId,
            @RequestParam Action action
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((TokenPrincipalModel) authentication.getPrincipal()).getEmail();

        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null.");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null.");
        }

        gameServiceImpl.processGameAction(gameId, action, username);
    }
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        if (!gameServiceImpl.getGameById(id).isPresent()) {
            throw new NotFoundException("Game not found with id: " + id);
        }

        gameServiceImpl.deleteGameById(id);
    }

    @PutMapping("/end/{gameId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void endGame(@PathVariable Long gameId){
        gameServiceImpl.endGame(gameId);
    }
}
