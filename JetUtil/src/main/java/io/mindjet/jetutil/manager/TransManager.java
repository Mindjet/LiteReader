package io.mindjet.jetutil.manager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.mindjet.jetutil.logger.JLogger;

/**
 * Created by Jet on 3/9/17.
 */

public class TransManager {

    private JLogger jLogger = JLogger.get(getClass().getSimpleName());
    private static TransManager manager;

    public static TransManager get() {
        manager = manager == null ? new TransManager() : manager;
        return manager;
    }

    public ActivityStep with(Activity activity) {
        return new ActivityStep(activity);
    }

    public ViewStep with(View view) {
        return new ViewStep(view);
    }

    public static class ViewStep {

        private View view;
        private int pivotX = 0;
        private int pivotY = 0;

        ViewStep(View view) {
            this.view = view;
        }

        public void startActivity(Intent intent) {
            ActivityOptionsCompat options =  ActivityOptionsCompat.makeClipRevealAnimation(view, pivotX, pivotY, view.getWidth(), view.getHeight());
            view.getContext().startActivity(intent, options.toBundle());
        }

        public ViewStep pivot(int pivotX, int pivotY) {
            this.pivotX = pivotX;
            this.pivotY = pivotY;
            return this;
        }

    }

    public static class ActivityStep {

        private WeakReference<Activity> activity;
        private List<Pair<View, String>> pairList = new ArrayList<>();
        private Intent intent;

        ActivityStep(Activity activity) {
            this.activity = new WeakReference<>(activity);
        }

        public <V extends View> ActivityStep pair(V view, @StringRes int string) {
            Pair<View, String> pair = new Pair<>((View) view, view.getContext().getResources().getString(string));
            pairList.add(pair);
            return this;
        }

        public void startActivity(Intent intent) {
            this.intent = intent;
            switch (pairList.size()) {
                case 0:
                    startActivity(intent);
                    break;
                case 1:
                    pair1();
                    break;
                case 2:
                    pair2();
                    break;
            }
        }

        private void pair1() {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity.get(), pairList.get(0)
            );
            activity.get().startActivity(intent, options.toBundle());
        }

        private void pair2() {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity.get(), pairList.get(0), pairList.get(1)
            );
            activity.get().startActivity(intent, options.toBundle());
        }

    }

}
