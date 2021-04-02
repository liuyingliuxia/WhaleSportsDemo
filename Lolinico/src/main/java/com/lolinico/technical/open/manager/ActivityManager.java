package com.lolinico.technical.open.manager;

import android.app.Activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------------<br />
 * desc：<br />
 * author：Darren Chen <br />
 * date：2019/1/8<br />
 * email：darren.chen@zeasn.com<br />
 * ---------------------------------------------------------<br />
 */
public class ActivityManager {

    private List<Activity> activities = new ArrayList<>();

    private ActivityManager(){}

    private static ActivityManager manager;

    public static ActivityManager getManager(){
        if (manager == null) {
            synchronized (ActivityManager.class) {
                if (manager == null) {
                    manager = new ActivityManager();
                }
            }
        }
        return manager;
    }

    /**
     * desc：用于获取根Activity，方便用于操作应用级别的提示<br />
     * author：Darren Chen<br />
     * date：2019/2/22<br />
     */
    public Activity getRootActivity(){
        if (activities.size() > 0) {
            return activities.get(0);
        }
        return null;
    }

    /**
     * desc：获取现在显示在界面上的Activity的引用<br />
     * author：Darren Chen<br />
     * date：2019/2/22<br />
     */
    public Activity getCurrentActivity(){
        if (activities.size() > 0) {
            return activities.get(activities.size() - 1);
        }
        return null;
    }


    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

    public void finishAndClearStack(){

        for (Activity activity : activities){
            activity.finish();
        }

        activities.clear();

    }





}
