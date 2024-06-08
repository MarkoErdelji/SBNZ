package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.UserGameDTO;
import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.enums.Action;
import com.ftn.sbnz.model.enums.Report;
import com.ftn.sbnz.repository.GameRepository;
import com.ftn.sbnz.repository.GameStatisticRepository;
import com.ftn.sbnz.repository.UserRepository;
import com.ftn.sbnz.service.intefaces.GameService;
import com.ftn.sbnz.service.intefaces.UserService;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Autowired
    KieSession kieSession;
    private final UserRepository userRepository;
    private final GameStatisticRepository gameStatisticRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, GameStatisticRepository gameStatisticRepository,KieContainer kieContainer) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gameStatisticRepository = gameStatisticRepository;
    }

    @Autowired
    UserService userService;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> getUnendedGames() {
        return gameRepository.findByIsEnded(false);
    }

   public GameStatistic getUserActiveGameStatistics(String username) {
    Long userId = userRepository.findByUsername(username).getId();
    System.out.println("User id"+userId);
    // Log the contents of the kieSession
    logKieSessionContents();

    QueryResults results = kieSession.getQueryResults("UserActiveGameStatistics", userId);


    for (QueryResultsRow row : results) {
        GameStatistic gameStatistic = (GameStatistic) row.get("$gameStatistic");
        return gameStatistic;
    }

    return null;
}

private void logKieSessionContents() {
    Collection<Object> facts = (Collection<Object>) kieSession.getObjects();
    System.out.println("Contents of kieSession:");
    for (Object fact : facts) {
        if (fact instanceof GameStatistic) {
            GameStatistic gameStatistic = (GameStatistic) fact;
            System.out.println("GameStatistic: " + gameStatistic.toString() + ", UserId: " + gameStatistic.getUserId());
        }else if (fact instanceof Game) {
            Game game = (Game) fact;
            System.out.println("Game: " + game.toString() + ", ENDED: " + game.isEnded());
        } else {
            System.out.println(fact.toString());
        }
    }
}


    @Override
    public void processGameAction(Long gameId, Action action, String username) {
        Game game = gameRepository.findById(gameId).get();
        GameStatistic modifiedGameStatistic = null;
        for (FactHandle factHandle : kieSession.getFactHandles()) {
            Object obj = kieSession.getObject(factHandle);
            System.out.println(kieSession.getObject(factHandle));
            if (obj instanceof Game && ((Game) obj).getId() == game.getId()) {
                game = (Game) obj;
            }

        }

        GameStatistic usersStatistic = null;
        for (GameStatistic statistic : game.getGameStatistics()){
            if (statistic.getUserId() == userService.getUserByUsername(username).getId()){
                usersStatistic = statistic;

            }
        }
        for (FactHandle factHandle : kieSession.getFactHandles()){
            Object obj = kieSession.getObject(factHandle);
            if (obj instanceof GameStatistic && ((GameStatistic) obj).getId() == usersStatistic.getId()) {
                modifiedGameStatistic = (GameStatistic) obj;
            }
        }
        usersStatistic.setReports(modifiedGameStatistic.getReports());
        int index = game.getGameStatistics().indexOf(usersStatistic);
        if (usersStatistic == null){ throw new IllegalArgumentException("User does not participate in this game!");}
        switch (action){
            case REGULAR_KILL:
            kieSession.insert(new RegularKillEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        case HEADSHOT_KILL:
            kieSession.insert(new HeadshotKillEvent(usersStatistic.getId()));
            kieSession.fireAllRules();
            break;
        case UTILITY_USAGE:
            kieSession.insert(new UtilityUsageEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        case ASSIST:
            kieSession.insert(new AssistEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        case WALLBANG_KILL:
            kieSession.insert(new WallbangEvent(usersStatistic.getId(),System.currentTimeMillis()));
            kieSession.fireAllRules();
            break;
        default:
            throw new IllegalArgumentException("Not a valid action!");
        }
        System.out.println(modifiedGameStatistic.getRegularKills());
        System.out.println("reportovi broj: " + modifiedGameStatistic.getReports().size());
        if (index != -1) {
            game.getGameStatistics().set(index, usersStatistic);
        }
//        gameStatisticRepository.save(usersStatistic);
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
        List<Game> unendedGames = gameRepository.findByIsEnded(false);
        for (Game game : unendedGames) {
            for (GameStatistic gameStatistic : game.getGameStatistics()) {
                if (userList.stream().anyMatch(user -> user.getId().equals(gameStatistic.getUserId()))) {
                    throw new IllegalArgumentException("One or more users are already in an ongoing game!");
                }
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
        kieSession.insert(game);
        return gameRepository.save(game);
    }

    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }

    @Override
    public void endGame(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game not found with id: " + gameId));
        game.setEnded(true);
        kieSession.insert(new GameEndedEvent(gameId));
        kieSession.fireAllRules();
        gameRepository.save(game);
    }

    public List<GameStatistic> getLast10GameStatistics(String username) {
        User user = userRepository.findByUsername(username);
        QueryResults results = kieSession.getQueryResults("Last 10 GameStatistics", user.getId());
        List<GameStatistic> gameStatistics = new ArrayList<>();

        for (QueryResultsRow row : results) {
            List<GameStatistic> gsList = (List<GameStatistic>) row.get("$gameStatistic");
            gameStatistics.addAll(gsList);
        }

        return gameStatistics;
    }
}
