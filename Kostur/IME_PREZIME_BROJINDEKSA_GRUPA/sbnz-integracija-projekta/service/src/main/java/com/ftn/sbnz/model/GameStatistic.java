package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.Report;
import org.hibernate.annotations.Type;
import org.kie.api.definition.type.Role;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="GameStatistic")
@Role(Role.Type.EVENT)
public class GameStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    public Long userId;
    @Column
    private long timestamp;
    @Column
    private int regularKills;
    @Column
    private int headshotKills;
    @Column
    private int assists;
    @Column
    private int wallbangKills;
    @Column
    private int utilityUsages;


    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Report.class)
    private List<Report> reports;

    public GameStatistic() {}

    public GameStatistic(Long userId, long timestamp,List<Report> reports) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.regularKills = 0;
        this.headshotKills = 0;
        this.assists = 0;
        this.wallbangKills = 0;
        this.utilityUsages = 0;
        this.reports = reports;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}