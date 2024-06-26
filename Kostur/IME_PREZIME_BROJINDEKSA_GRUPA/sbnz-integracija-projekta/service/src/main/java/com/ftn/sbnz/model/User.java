package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.SuspicionLevel;
import com.ftn.sbnz.model.enums.UserType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SuspicionLevel suspicionLevel;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Achievement> achievements;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    @Column
    private boolean isSuspended;
    @Column
    private String password;

    @Column
    private UserType userType;
    public User() {}

    public User(SuspicionLevel suspicionLevel, List<Achievement> achievements) {
        this.suspicionLevel = suspicionLevel;
        this.achievements = achievements;
        this.isSuspended = false;
    }

        public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", suspicionLevel=" + suspicionLevel +
                ", username='" + username + '\'' +
                ", isSuspended=" + isSuspended +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}