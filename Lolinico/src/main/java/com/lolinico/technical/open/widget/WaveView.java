package com.lolinico.technical.open.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.lolinico.technical.open.utils.RLog;
import com.lolinico.technical.open.utils.WidgetUtils;
import com.steelbar.R;

import java.lang.ref.WeakReference;

/**
 * Created by John on 2014/10/15.
 */
public class WaveView extends LinearLayout {
    protected static final int LARGE = 1;
    protected static final int MIDDLE = 2;
    protected static final int LITTLE = 3;

    private int mWaveFromColor;
    private int mWaveToColor;
    private int mProgress = -1;
    private int mWaveHeight;
    private int mWaveMultiple;
    private int mWaveHz;

    private int mWaveToTop;

    private Wave mWave;
    private Solid mSolid;

    private final int DEFAULT_PROGRESS = 1;

    int mLastProgress = 1;

    //水波纹上升
    private final static int MSG_PROGRESS_UP = 101;
    //水波纹下降
    private final static int MSG_PROGRESS_DOWN = 102;
    int beginY;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.TRANSPARENT);
        //load styled attributes.
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WaveView, R.attr.waveViewStyle, 0);
        mWaveFromColor = attributes.getColor(R.styleable.WaveView_wave_from_color, getContext().getResources().getColor(R.color.wave_from_color));
        mWaveToColor = attributes.getColor(R.styleable.WaveView_wave_to_color, getContext().getResources().getColor(R.color.wave_to_color));
        mProgress = attributes.getInt(R.styleable.WaveView_progress, DEFAULT_PROGRESS);
        mWaveHeight = attributes.getInt(R.styleable.WaveView_wave_height, MIDDLE);
        mWaveMultiple = attributes.getInt(R.styleable.WaveView_wave_length, LARGE);
        mWaveHz = attributes.getInt(R.styleable.WaveView_wave_hz, MIDDLE);
        attributes.recycle();


        mWave = new Wave(context, null);
        mWave.initializeWaveSize(mWaveMultiple, mWaveHeight, mWaveHz);
        mWave.setAboveWaveColor(mWaveFromColor, mWaveToColor);
        mWave.initializePainters();

        mSolid = new Solid(context, null);
        mSolid.setAboveWavePaint(mWave.getAboveWavePaint());
        mSolid.setBlowWavePaint(mWave.getBlowWavePaint());
        beginY = WidgetUtils.getDm(getContext()).heightPixels;
        addView(mWave);
        addView(mSolid);

        setProgress(mProgress);
    }

    public void setmLastProgress() {
        this.mLastProgress = 1;
    }

    /**
     * 设置顶部TOP
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress < 0)
            return;
        mLastProgress = progress;
        this.mProgress = progress > 100 ? 100 : progress;
        computeWaveToTop();
    }

    /**
     * 设置平移动画
     *
     * @param progress
     */
    public void setAnProgress(int progress) {
        if (progress == mLastProgress)
            return;
        this.mProgress = progress > 100 ? 100 : progress;
        computeWaveAn(progress, 550, 0);
        mLastProgress = progress;
    }

    /**
     * 设置平移动画
     *
     * @param progress
     */
    public void setAnProgress(int progress, int delayTime) {
        if (progress == mLastProgress)
            return;
        this.mProgress = progress > 100 ? 100 : progress;
        computeWaveAn(progress, 550, delayTime);
        mLastProgress = progress;
    }


    /**
     * 设置平移动画
     *
     * @param progress
     */
    public void setAnProgress(int progress, long duration, int delayTime) {
        if (progress == mLastProgress)
            return;
        this.mProgress = progress > 100 ? 100 : progress;
        computeWaveAn(progress, duration, delayTime);
        mLastProgress = progress;
    }


    void computeWaveAn(int progress, long duration, int delayTime) {
        if (getTag() != null)
            ((ObjectAnimator) getTag()).cancel();
        mWaveToTop = (int) (WidgetUtils.getDm(getContext()).heightPixels * (1f - progress * 0.01));
        if (beginY != mWaveToTop) {
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", beginY, mWaveToTop);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhY).setDuration(duration);
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.setStartDelay(delayTime);
            objectAnimator.start();
            setTag(objectAnimator);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (getVisibility() != VISIBLE)
                        setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (getVisibility() != VISIBLE)
                        setVisibility(VISIBLE);
                }
            });
            beginY = mWaveToTop;
        }
    }

    /***
     * 直接平移
     * @param fromProgress
     * @param progress
     * @param duration
     */
    public void tranlWaveTop(int fromProgress, int progress, int waveTop, long duration) {
        WidgetUtils.setViewMarginsFixedParams(getContext(), this, 0, 0, 0, (int) (WidgetUtils.getDm(getContext()).heightPixels * (1f - waveTop * 0.01)), 0, 0);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", (int) (WidgetUtils.getDm(getContext()).heightPixels * (1f - fromProgress * 0.01)), (int) (WidgetUtils.getDm(getContext()).heightPixels * (1f - progress * 0.01)));
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhY).setDuration(duration);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    private void computeWaveToTop() {
        mWaveToTop = (int) (WidgetUtils.getDm(getContext()).heightPixels * (1f - mProgress * 0.01));
        WidgetUtils.setViewMarginsFixedParams(getContext(), this, 0, 0, 0, mWaveToTop, 0, 0);
    }

    public void computeWaveToTop(int progress) {
        mWaveToTop = (int) (WidgetUtils.getDm(getContext()).heightPixels * (1f - progress / 100f));
        WidgetUtils.setViewMarginsFixedParams(getContext(), this, 0, 0, 0, mWaveToTop, 0, 0);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        // Force our ancestor class to save its state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.progress = mProgress;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setProgress(ss.progress);
    }

    private static class SavedState extends BaseSavedState {
        int progress;

        /**
         * Constructor called from {@link android.widget.ProgressBar#onSaveInstanceState()}
         */
        SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            progress = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(progress);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 水波纹动画过程
     *
     * @param start 初始高度
     * @param end   结束时高度
     */
    public void setProgressByAni(int start, int end) {
        //先初始化水波纹高度
        setProgress(start);
        if (start < end) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_PROGRESS_UP, start, end));
        } else {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_PROGRESS_DOWN, start, end));
        }
    }

    WaveVieWHandler mHandler = new WaveVieWHandler(this);

    private static class WaveVieWHandler extends Handler {
        private WeakReference<WaveView> waveViewWeakReference;

        public WaveVieWHandler(WaveView view) {
            this.waveViewWeakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {

            WaveView waveView = this.waveViewWeakReference.get();
            if (waveView == null) return;

            int start = msg.arg1;
            int end = msg.arg2;
            switch (msg.what) {
                case MSG_PROGRESS_UP:
                    if (start < end) {
                        start++;
                        waveView.setProgress(start);
                        sendMessage(obtainMessage(MSG_PROGRESS_UP, start, end));
                    }
                    break;
                case MSG_PROGRESS_DOWN:
                    if (start > end) {
                        start--;
                        waveView.setProgress(start);
                        sendMessage(obtainMessage(MSG_PROGRESS_DOWN, start, end));
                    }
                    break;
            }
        }
    }

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            int start = msg.arg1;
//            int end = msg.arg2;
//            switch (msg.what) {
//                case MSG_PROGRESS_UP:
//                    if (start < end) {
//                        start++;
//                        setProgress(start);
//                        mHandler.sendMessage(mHandler.obtainMessage(MSG_PROGRESS_UP, start, end));
//                    }
//                    break;
//                case MSG_PROGRESS_DOWN:
//                    if (start > end) {
//                        start--;
//                        setProgress(start);
//                        mHandler.sendMessage(mHandler.obtainMessage(MSG_PROGRESS_DOWN, start, end));
//                    }
//                    break;
//            }
//        }
//    };


}
