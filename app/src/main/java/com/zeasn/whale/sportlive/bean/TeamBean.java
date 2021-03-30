package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

/**
 * 队伍实体
 */
public class TeamBean implements Serializable {
    String tNm;//队名
    String tIcon;
    int gtHome;//是否主队
    int gtScore;
    int gtWin;
}
