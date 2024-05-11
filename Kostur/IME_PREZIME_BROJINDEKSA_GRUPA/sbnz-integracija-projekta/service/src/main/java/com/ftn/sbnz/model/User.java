package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.SuspicionLevel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SuspicionLevel suspicionLevel;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Achievement> achievements;

    public User() {}

    public User(SuspicionLevel suspicionLevel, List<Achievement> achievements) {
        this.suspicionLevel = suspicionLevel;
        this.achievements = achievements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuspicionLevel getSuspicionLevel() {
        return suspicionLevel;
    }

    public void setSuspicionLevel(SuspicionLevel suspicionLevel) {
        this.suspicionLevel = suspicionLevel;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
}