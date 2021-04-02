package com.lolinico.technical.open.utils;

import android.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.math.BigDecimal;

/**
 * Created by Rico on 2017/5/15.
 */
public class WidgetUtils {

    private static double coefficient = 1920;

    /**
     * 设置比例计算系数
     *
     * @param coefficient
     */
    public static void setCoefficient(double coefficient) {
        WidgetUtils.coefficient = coefficient;
    }

    /**
     * 获取屏幕DisplayMetrics
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDm(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm;
    }

    /**
     * 设置高宽
     * 参数设置为0则使用布局属性
     *
     * @param context
     * @param viewGroup
     * @param x         基于屏幕宽度为1920的px长度，下同
     * @param y         px
     */
    public static void setViewParamsForPx(Context context, View viewGroup, double x, double y) {
        double ratio_x, ratio_y;
        ratio_x = computationalRatio(x);
        ratio_y = computationalRatio(y);

        DisplayMetrics dm = WidgetUtils.getDm(context);
        if (viewGroup.getParent() != null) {
            int pixels = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
            if (viewGroup.getParent() instanceof LinearLayout) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof FrameLayout) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof ViewGroup) {
                ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                viewGroup.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置宽高、间距
     * 参数设置为0则使用布局属性
     *
     * @param context
     * @param viewGroup
     * @param x         基于屏幕宽度为1920的px长度，下同
     * @param y         px
     * @param left      px
     * @param top       px
     * @param right     px
     * @param bottom    px
     */
    public static void setViewMarginsParamsForPx(Context context, View viewGroup, double x, double y, double left, double top, double right, double bottom) {

        double ratio_x, ratio_y, ratio_left, ratio_right, ratio_top, ratio_bottom;
        ratio_x = computationalRatio(x);
        ratio_y = computationalRatio(y);
        ratio_left = computationalRatio(left);
        ratio_right = computationalRatio(right);
        ratio_top = computationalRatio(top);
        ratio_bottom = computationalRatio(bottom);
        Log.d("zeasn", "double:" + ratio_x + "-" + ratio_y + "-" + ratio_left + "-" + ratio_right + "-" + ratio_top + "-" + ratio_bottom);

        DisplayMetrics dm = WidgetUtils.getDm(context);
        if (viewGroup.getParent() != null) {
            int pixels = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
            if (viewGroup.getParent() instanceof LinearLayout) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                params.setMargins((int) (ratio_left * pixels), (int) (ratio_top * pixels), (int) (ratio_right * pixels), (int) (ratio_bottom * pixels));
                params.setMarginStart((int) (ratio_left * pixels));
                params.setMarginEnd((int) (ratio_right * pixels));
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                params.setMargins((int) (ratio_left * pixels), (int) (ratio_top * pixels), (int) (ratio_right * pixels), (int) (ratio_bottom * pixels));
                params.setMarginStart((int) (ratio_left * pixels));
                params.setMarginEnd((int) (ratio_right * pixels));
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof FrameLayout) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                params.setMargins((int) (ratio_left * pixels), (int) (ratio_top * pixels), (int) (ratio_right * pixels), (int) (ratio_bottom * pixels));
                params.setMarginStart((int) (ratio_left * pixels));
                params.setMarginEnd((int) (ratio_right * pixels));
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof ViewGroup) {
                ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
                if (ratio_x > 0) {
                    params.width = (int) (pixels * ratio_x);
                }
                if (ratio_y > 0) {
                    params.height = (int) (pixels * ratio_y);
                }
                viewGroup.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置高宽
     * 直接传比例
     *
     * @param context
     * @param viewGroup
     * @param x
     * @param y
     */
    public static void setViewParams(Context context, View viewGroup, double x, double y) {
        DisplayMetrics dm = WidgetUtils.getDm(context);
        if (viewGroup.getParent() != null) {
            int pixels = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
            if (viewGroup.getParent() instanceof LinearLayout) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof FrameLayout) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof ViewGroup) {
                ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                viewGroup.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置高宽 间距
     * 直接传比例
     *
     * @param context
     * @param viewGroup
     * @param x
     * @param y
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setViewMarginsParams(Context context, View viewGroup, double x, double y, double left, double top, double right, double bottom) {
        DisplayMetrics dm = WidgetUtils.getDm(context);
        if (viewGroup.getParent() != null) {
            int pixels = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
            if (viewGroup.getParent() instanceof LinearLayout) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                params.setMargins((int) (left * pixels), (int) (top * pixels), (int) (right * pixels), (int) (bottom * pixels));
                params.setMarginStart((int) (left * pixels));
                params.setMarginEnd((int) (right * pixels));
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                params.setMargins((int) (left * pixels), (int) (top * pixels), (int) (right * pixels), (int) (bottom * pixels));
                params.setMarginStart((int) (left * pixels));
                params.setMarginEnd((int) (right * pixels));
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof FrameLayout) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                params.setMargins((int) (left * pixels), (int) (top * pixels), (int) (right * pixels), (int) (bottom * pixels));
                params.setMarginStart((int) (left * pixels));
                params.setMarginEnd((int) (right * pixels));
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof ViewGroup) {
                ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = (int) (pixels * x);
                }
                if (y > 0) {
                    params.height = (int) (pixels * y);
                }
                viewGroup.setLayoutParams(params);
            }
        }
    }

    /***
     * 获取比例长宽
     *
     * @param context
     * @param x
     * @param y
     * @return
     */
    public static int[] getXY(Context context, double x, double y) {
        DisplayMetrics dm = WidgetUtils.getDm(context);
        int pixels = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
        int[] xy = new int[2];
        if (x > 0) {
            xy[0] = (int) (pixels * x);
        }
        if (y > 0) {
            xy[1] = (int) (pixels * y);
        }
        return xy;
    }

    public static int getValue(Context context, double percentage) {
        DisplayMetrics dm = WidgetUtils.getDm(context);
        int pixels = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
        return (int) (pixels * percentage);
    }

    /**
     * @param context
     * @param viewGroup
     * @param x
     * @param y
     */
    public static void setViewFixedParams(Context context, View viewGroup, int x, int y) {
        if (viewGroup.getParent() != null) {
            if (viewGroup.getParent() instanceof LinearLayout) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof FrameLayout) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof ViewGroup) {
                ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                viewGroup.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置高宽 间距
     *
     * @param context
     * @param viewGroup
     * @param x
     * @param y
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setViewMarginsFixedParams(Context context, View viewGroup, int x, int y, int left, int top, int right, int bottom) {
        if (viewGroup.getParent() != null) {
            if (viewGroup.getParent() instanceof LinearLayout) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                params.setMargins(left, top, right, bottom);
                params.setMarginStart(left);
                params.setMarginEnd(right);
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                params.setMargins(left, top, right, bottom);
                params.setMarginStart(left);
                params.setMarginEnd(right);
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof FrameLayout) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                params.setMargins(left, top, right, bottom);
                params.setMarginStart(left);
                params.setMarginEnd(right);
                viewGroup.setLayoutParams(params);
            } else if (viewGroup.getParent() instanceof ViewGroup) {
                ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
                if (x > 0) {
                    params.width = x;
                }
                if (y > 0) {
                    params.height = y;
                }
                viewGroup.setLayoutParams(params);
            }
        }
    }

    /**
     * 计算比例
     * 通过BigDecimal四舍五入
     *
     * @param d
     * @return double
     */
    private static double computationalRatio(double d) {
        return new BigDecimal(d / coefficient).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
