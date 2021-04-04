package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

/**
 * 队伍实体
 */
public class TeamBean extends BaseBean implements Serializable {

    int gtHome;//是否主队
    int gtScore;
    int gtWin;

    public TeamBean(int teamLogoId , String teamName){
        logoResId = teamLogoId;
        titleName = teamName;
    }

}
