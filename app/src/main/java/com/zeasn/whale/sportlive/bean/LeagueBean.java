package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe:联盟实体
 */
public class LeagueBean  extends BaseBean implements Serializable {

    public LeagueBean(int mResId , String mLeaName){
        logoResId = mResId;
        titleName = mLeaName;
    }
}
