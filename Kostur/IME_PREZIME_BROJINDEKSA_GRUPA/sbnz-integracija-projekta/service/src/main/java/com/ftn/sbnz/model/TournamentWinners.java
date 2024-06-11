package com.ftn.sbnz.model;

import java.util.ArrayList;
import java.util.List;

public class TournamentWinners {
    private List<String> winners;

    public TournamentWinners() {
        this.winners = new ArrayList<String>();
    }
    public void addWinner(String winner) {
        this.winners.add(winner);
    }
    public List<String> getWinners() {
        return winners;
    }

    public void setWinners(List<String> winners) {
        this.winners = winners;
    }
}
