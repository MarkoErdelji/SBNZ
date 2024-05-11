package com.ftn.sbnz.model;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean analyzed;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameStatistic> gameStatistics;

    public Game() {}

    public Game(boolean analyzed, List<GameStatistic> gameStatistics) {
        this.analyzed = analyzed;
        this.gameStatistics = gameStatistics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    public List<GameStatistic> getGameStatistics() {
        return gameStatistics;
    }

    public void setGameStatistics(List<GameStatistic> gameStatistics) {
        this.gameStatistics = gameStatistics;
    }
}
