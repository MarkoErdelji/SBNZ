package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.AccumulatedStatsDTO;
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
import org.kie.api.runtime.rule.Variable;
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
        System.out.println(row.toString());
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
        Long userId = userRepository.findByUsername(username).getId();
           QueryResults results = kieSession.getQueryResults("UserActiveGameStatistics",userId);

            GameStatistic usersStatistic = null;
            for (QueryResultsRow row : results) {
                System.out.println(row.toString());
                GameStatistic gameStatistic = (GameStatistic) row.get("$gameStatistic");
                usersStatistic =  gameStatistic;
            }
            if (usersStatistic == null){
                throw new IllegalArgumentException("User is not currently in a game!");
            }
        switch (action){
            case REGULAR_KILL:
            kieSession.insert(new RegularKillEvent(usersStatistic.getId(),System.currentTimeMillis(),userId));
            kieSession.fireAllRules();
            break;
        case HEADSHOT_KILL:
            kieSession.insert(new HeadshotKillEvent(usersStatistic.getId(),userId));
            kieSession.fireAllRules();
            break;
        case UTILITY_USAGE:
            kieSession.insert(new UtilityUsageEvent(usersStatistic.getId(),System.currentTimeMillis(),userId));
            kieSession.fireAllRules();
            break;
        case ASSIST:
            kieSession.insert(new AssistEvent(usersStatistic.getId(),System.currentTimeMillis(),userId));
            kieSession.fireAllRules();
            break;
        case WALLBANG_KILL:
            kieSession.insert(new WallbangEvent(usersStatistic.getId(),System.currentTimeMillis(),userId));
            kieSession.fireAllRules();
            break;
        default:
            throw new IllegalArgumentException("Not a valid action!");
        }
    }

    public AccumulatedStatsDTO getUserAccumulatedStats(Long userId, String timePeriod) {
    List<GameStatistic> gameStatistics;
    switch (timePeriod) {
        case "1d":
            gameStatistics = getLast1DayStatistics(userId);
            break;
        case "7d":
            gameStatistics = getLast7DaysStatistics(userId);
            break;
        case "30d":
            gameStatistics = getLast30DaysStatistics(userId);
            break;
        default:
            throw new IllegalArgumentException("Invalid time period: " + timePeriod);
    }

    AccumulatedStatsDTO accumulatedStats = calculateAccumulatedStats(gameStatistics);

    return accumulatedStats;
}


private List<GameStatistic> getLast1DayStatistics(Long userId) {
    return getGameStatisticsFromQuery("getLast1DayStatistics", userId);
}

private List<GameStatistic> getLast7DaysStatistics(Long userId) {
    return getGameStatisticsFromQuery("getLast7DaysStatistics", userId);
}

private List<GameStatistic> getLast30DaysStatistics(Long userId) {
    return getGameStatisticsFromQuery("getLast30DaysStatistics", userId);
}

private List<GameStatistic> getGameStatisticsFromQuery(String queryName, Long userId) {
    List<GameStatistic> gameStatistics = new ArrayList<>();
    QueryResults queryResults = kieSession.getQueryResults(queryName, userId);
    for (QueryResultsRow row : queryResults) {
        @SuppressWarnings("unchecked")
        List<GameStatistic> gameStatisticsList = (List<GameStatistic>) row.get("$gameStatistics");
        for (GameStatistic statistic : gameStatisticsList) {
            System.out.println(statistic.toString());
        }
        gameStatistics.addAll(gameStatisticsList);
    }
    return gameStatistics;
}

    private AccumulatedStatsDTO calculateAccumulatedStats(List<GameStatistic> gameStatistics) {
    int totalRegularKills = 0;
    int totalHeadshotKills = 0;
    int totalAssists = 0;
    int totalWallbangKills = 0;
    int totalUtilityUsages = 0;

    for (GameStatistic gameStatistic : gameStatistics) {
        totalRegularKills += gameStatistic.getRegularKills();
        totalHeadshotKills += gameStatistic.getHeadshotKills();
        totalAssists += gameStatistic.getAssists();
        totalWallbangKills += gameStatistic.getWallbangKills();
        totalUtilityUsages += gameStatistic.getUtilityUsages();
    }


    double headshotPercentage = (totalRegularKills > 0) ? ((double) totalHeadshotKills / totalRegularKills) * 100 : 0;
    double wallbangPercentage = (totalRegularKills > 0) ? ((double) totalWallbangKills / totalRegularKills) * 100 : 0;


    return new AccumulatedStatsDTO(totalRegularKills, totalHeadshotKills, totalAssists, totalWallbangKills, totalUtilityUsages, headshotPercentage, wallbangPercentage);
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

    Game game = new Game(false, new ArrayList<>());
    game = gameRepository.save(game);

    long timestamp = System.currentTimeMillis();
    List<GameStatistic> gameStatistics = new ArrayList<>();
    for (User user : userList) {
        List<Report> initialReports = new ArrayList<>();
        GameStatistic gameStatistic = new GameStatistic(user.getId(), timestamp, initialReports, game.getId());
        gameStatistic = gameStatisticRepository.save(gameStatistic);
        gameStatistics.add(gameStatistic);
        kieSession.insert(gameStatistic);
    }
    game.setGameStatistics(gameStatistics);
    gameRepository.save(game);
    kieSession.insert(game);
    return game;
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

    public TournamentWinners getTournamentWinner(String tournamentName){
        TournamentQueryEvent tournamentQueryEvent = new TournamentQueryEvent(tournamentName);

        kieSession.insert(tournamentQueryEvent);
        kieSession.fireAllRules();
        QueryResults queryResults = kieSession.getQueryResults("isContainedIn", tournamentName, Variable.v);

        TournamentWinners tournamentWinners = new TournamentWinners();
        List<String> imeList = new ArrayList<>();
        for (QueryResultsRow row : queryResults) {
            Tournament tournament = (Tournament) row.get("$tournament");

                imeList.add(tournament.getString2());


        }
        tournamentWinners.setWinners(imeList);
        return tournamentWinners;



    }
}
