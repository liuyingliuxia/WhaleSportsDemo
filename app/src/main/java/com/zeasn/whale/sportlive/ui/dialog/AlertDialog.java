package com.zeasn.whale.sportlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.util.RLog;
import com.zeasn.whale.sportlive.widget.DensityUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @deprecated
 */
public class AlertDialog extends Dialog {
    @BindView(R.id.tvOKBtn)
    TextView tvOk;

    Context mContext;

    public AlertDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_alert, null);
        setContentView(view);
        view.setBackgroundColor(view.getContext().getResources().getColor(R.color.half_black));
        int width = DensityUtil.getWindowWidth(mContext);
        int height = DensityUtil.getWindowHeight(mContext);
        RLog.v("AlertDialog width = " + width + ",height = " + height);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = width / 2;
        params.height = (int) (height / 2.7);
        getWindow().setAttributes(params);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.CENTER_HORIZONTAL
                | Gravity.CENTER_VERTICAL);

        ButterKnife.bind(this, view);
        tvOk.setOnClickListener(v -> {
            dismiss();
        });
    }
}
