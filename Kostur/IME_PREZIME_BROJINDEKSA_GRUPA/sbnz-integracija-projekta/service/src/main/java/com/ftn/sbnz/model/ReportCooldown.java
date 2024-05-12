package com.ftn.sbnz.model;


import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class ReportCooldown {

     private Long gameStatisticId;

    public ReportCooldown() {
    }

    public ReportCooldown(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }

    public Long getGameStatisticId() {
        return gameStatisticId;
    }

    public void setGameStatisticId(Long gameStatisticId) {
        this.gameStatisticId = gameStatisticId;
    }
}
