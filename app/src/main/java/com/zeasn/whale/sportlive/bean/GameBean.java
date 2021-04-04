package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

/**
 * 比赛实体
 */
public class GameBean extends BaseBean implements Serializable {

    String lName;
    int round;
    int status;
    int type;
    TeamBean teamOne;//主场
    TeamBean teamTwo;//客场
}
