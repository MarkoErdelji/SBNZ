package com.ftn.sbnz.dto;

public class AccumulatedStatsDTO {
    private int regularKills;
    private int headshotKills;
    private int assists;
    private int wallbangKills;
    private int utilityUsages;
    private double headshotPercentage;
    private double wallbangPercentage;

    // Constructors
    public AccumulatedStatsDTO() {
    }

    public AccumulatedStatsDTO(int regularKills, int headshotKills, int assists, int wallbangKills, int utilityUsages, double headshotPercentage, double wallbangPercentage) {
        this.regularKills = regularKills;
        this.headshotKills = headshotKills;
        this.assists = assists;
        this.wallbangKills = wallbangKills;
        this.utilityUsages = utilityUsages;
        this.headshotPercentage = headshotPercentage;
        this.wallbangPercentage = wallbangPercentage;
    }

    // Getters and setters
    public int getRegularKills() {
        return regularKills;
    }

    public void setRegularKills(int regularKills) {
        this.regularKills = regularKills;
    }

    public int getHeadshotKills() {
        return headshotKills;
    }

    public void setHeadshotKills(int headshotKills) {
        this.headshotKills = headshotKills;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getWallbangKills() {
        return wallbangKills;
    }

    public void setWallbangKills(int wallbangKills) {
        this.wallbangKills = wallbangKills;
    }

    public int getUtilityUsages() {
        return utilityUsages;
    }

    public void setUtilityUsages(int utilityUsages) {
        this.utilityUsages = utilityUsages;
    }

    public double getHeadshotPercentage() {
        return headshotPercentage;
    }

    public void setHeadshotPercentage(double headshotPercentage) {
        this.headshotPercentage = headshotPercentage;
    }

    public double getWallbangPercentage() {
        return wallbangPercentage;
    }

    public void setWallbangPercentage(double wallbangPercentage) {
        this.wallbangPercentage = wallbangPercentage;
    }
}
