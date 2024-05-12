package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.UserGameDTO;
import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.enums.Action;
import com.ftn.sbnz.model.enums.Report;
import com.ftn.sbnz.repository.GameRepository;
import com.ftn.sbnz.service.intefaces.GameService;
import com.ftn.sbnz.service.intefaces.UserService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Autowired
    KieSession kieSession;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, KieContainer kieContainer) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    UserService userService;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public void processGameAction(Long gameId, Action action, String username) {
        Game game = gameRepository.findById(gameId).get();
        for (FactHandle factHandle : kieSession.getFactHandles()) {
            System.out.println(kieSession.getObject(factHandle));
        }
        GameStatistic usersStatistic = null;
        for (GameStatistic statistic : game.getGameStatistics()){
            if (statistic.getUserId() == userService.getUserByUsername(username).getId()){
                usersStatistic = statistic;
            }
        }
        if (usersStatistic == null){ throw new IllegalArgumentException("User does not participate in this game!");}
        switch (action){
            case REGULAR_KILL:
            usersStatistic.setRegularKills(usersStatistic.getRegularKills()+1);
            kieSession.insert(new RegularKillEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        case HEADSHOT_KILL:
            usersStatistic.setHeadshotKills(usersStatistic.getHeadshotKills()+1);
            kieSession.insert(new HeadshotKillEvent(usersStatistic.getId()));
            kieSession.fireAllRules();
            break;
        case UTILITY_USAGE:
            usersStatistic.setUtilityUsages(usersStatistic.getUtilityUsages()+1);
            kieSession.insert(new UtilityUsageEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        case ASSIST:
            usersStatistic.setAssists(usersStatistic.getAssists()+1);
            kieSession.insert(new AssistEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        case WALLBANG_KILL:
            usersStatistic.setWallbangKills(usersStatistic.getWallbangKills()+1);
            kieSession.insert(new WallbangEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        default:
            throw new IllegalArgumentException("Not a valid action!");
        }
         gameRepository.save(game);
    }

    public Game saveGame(List<UserGameDTO> userDTOs) {
        List<User> userList = new ArrayList<>();
        for (UserGameDTO userDTO : userDTOs) {
            User user = userService.getUserByUsername(userDTO.getUsername());
            if (user != null) {
                userList.add(user);
            } else {
                throw new NotFoundException( "User  " + userDTO.getUsername() + " not found.");
            }
        }

        List<GameStatistic> gameStatistics = new ArrayList<>();
        long timestamp = System.currentTimeMillis();
        for (User user : userList) {
            List<Report> initialReports = new ArrayList<>();
            GameStatistic gameStatistic = new GameStatistic(user.getId(), timestamp, initialReports);
            gameStatistics.add(gameStatistic);
            kieSession.insert(gameStatistic);
        }

        Game game = new Game(false, gameStatistics);
        return gameRepository.save(game);
    }

    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }
}
