package com.zeasn.whale.sportlive.ui.dialog;

import android.content.Context;
import android.view.View;

import com.zeasn.whale.sportlive.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author:Miracle.Lin
 * Date:2021/4/7
 * Email:miracle.lin@zeasn.com
 * Descripe:
 * @deprecated
 */
public class GameAlertDialogBasePopUp extends BasePopupWindow {
    public GameAlertDialogBasePopUp(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.dialog_alert);
    }



}
