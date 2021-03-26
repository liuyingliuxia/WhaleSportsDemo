package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

public class GameBean implements Serializable {

    int id;
    String gName;
    String lName;
    int round;
    int status;
    int type;
    TeamBean teamOne;//主场
    TeamBean teamTwo;//客场
}
