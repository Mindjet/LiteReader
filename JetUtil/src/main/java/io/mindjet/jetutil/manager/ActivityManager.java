package io.mindjet.jetutil.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Jet on 2/7/17.
 */

public class ActivityManager {

    private static Stack<Activity> activityStack = new Stack<>();

    /**
     * Push activity to the stack.
     *
     * @param activity target activity
     */
    public static void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * Get the current activity, that's, the activity on the top.
     *
     * @return current activity
     */
    public static Activity currentActivity() {
        if (activityStack.empty()) {
            return null;
        } else {
            return activityStack.peek();
        }
    }

    /**
     * Finish current activity.
     */
    public static void finishActivity() {
        if (!activityStack.empty())
            finishActivity(activityStack.peek());
    }

    /**
     * Finish an activity with a specific class.
     *
     * @param clz the class of the activity to finish
     */
    public static void finishActivity(Class<?> clz) {
        if (!activityStack.empty()) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(clz)) {
                    activityStack.remove(activity);
                    activity.finish();
                }
            }
        }
    }

    /**
     * Finish a specific activity.
     *
     * @param activity the activity to finish
     */
    public static void finishActivity(Activity activity) {
        if (!activityStack.empty() && activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

}
