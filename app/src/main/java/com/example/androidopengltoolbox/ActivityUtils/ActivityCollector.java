package com.example.androidopengltoolbox.ActivityUtils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

// ActivityCollector 类作为活动管理器, 可以管理所有的activity
public class ActivityCollector {
    public static List<Activity> activityList = new ArrayList<>();

    // 保存activity
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 移除activity
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    // 注销所有的activity
    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
