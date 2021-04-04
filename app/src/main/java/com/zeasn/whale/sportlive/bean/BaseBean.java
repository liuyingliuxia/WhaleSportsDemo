package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

/**
 * Author:Miracle.Lin
 * Date:2021/4/4
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class BaseBean implements Serializable {
    int logoResId;
    String titleName;

    public int getLogoResId() {
        return logoResId;
    }

    public void setLogoResId(int logoResId) {
        this.logoResId = logoResId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }


}
