package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

/**
 * 比赛实体
 */
public class GameBean implements Serializable {

    String gameName; //比赛名称
    int round;
    boolean status;// isLive
    int type;
    TeamBean teamA;//主场
    TeamBean teamB;//客场

    public GameBean(String mGName , TeamBean t1 , TeamBean t2 , boolean isLive){
        gameName = mGName;
        teamA = t1;
        teamB = t2;
        status = isLive;
    }
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TeamBean getTeamA() {
        return teamA;
    }

    public void setTeamA(TeamBean teamA) {
        this.teamA = teamA;
    }

    public TeamBean getTeamB() {
        return teamB;
    }

    public void setTeamB(TeamBean teamB) {
        this.teamB = teamB;
    }


}
