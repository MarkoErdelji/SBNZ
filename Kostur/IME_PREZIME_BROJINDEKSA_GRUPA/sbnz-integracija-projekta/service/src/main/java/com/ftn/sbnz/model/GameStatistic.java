package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.Report;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="GameStatistic")
public class GameStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int userId;
    @Column
    private long timestamp;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Report.class)
    private List<Report> reports;

    public GameStatistic() {}

    public GameStatistic(int userId, long timestamp, List<Report> reports) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.reports = reports;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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