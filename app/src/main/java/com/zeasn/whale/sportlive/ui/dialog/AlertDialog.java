package com.zeasn.whale.sportlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.util.RLog;
import com.zeasn.whale.sportlive.widget.DensityUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertDialog extends Dialog {
    @BindView(R.id.tvOk)
    TextView tvOk;
    @BindView(R.id.cbOne)
    CheckBox cbAlert;
    @BindView(R.id.cbTwo)
    CheckBox cbStart;
    @BindView(R.id.cbThree)
    CheckBox cbRealScore;
    @BindView(R.id.cbFour)
    CheckBox cbResult;

    Context mContext;

    public AlertDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_alert, null);
        setContentView(view);
        int width = DensityUtil.getWindowWidth(mContext);
        int height = DensityUtil.getWindowHeight(mContext);
        RLog.v("AlertDialog width = " + width + ",height = " + height);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = width / 2;
        params.height = (int) (height / 1.7);
        getWindow().setAttributes(params);

        Objects.requireNonNull(getWindow()).setGravity(Gravity.CENTER_HORIZONTAL
                | Gravity.CENTER_VERTICAL);

        ButterKnife.bind(this, view);
        tvOk.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void doShowAction() {
        show();
    }
}
