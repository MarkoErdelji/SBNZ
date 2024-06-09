package com.ftn.sbnz.model;

public class TournamentQueryEvent {
    private String tournamentName;

    public TournamentQueryEvent(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentName() {

        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
}
