package com.zeasn.whale.sportlive.bean;

import java.io.Serializable;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe:体育项目实体
 * 自定义 无接口返回
 */
public class SportBean extends BaseBean implements Serializable {


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isSelected;

    public SportBean(int mId, String mSpName) {
        logoResId = mId;
        titleName = mSpName;
    }
}
