package io.mindjet.jetutil.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.view.View;
import android.view.ViewAnimationUtils;

import io.mindjet.jetutil.R;
import io.mindjet.jetutil.logger.JLogger;
import io.mindjet.jetutil.version.VersionUtil;

/**
 * Created by Jet on 3/7/17.
 */

public class AnimUtil {

    private static boolean takeEffect = VersionUtil.afterLollipop();
    private static JLogger jLogger = JLogger.get("AnimUtil");
    private final static String REVEAL_WARNING = "Your version is too old to support reveal animation. Please update to Lollipop or later.";

    private static void reveal(final View view, final int duration, final int centerX, final int centerY, final float startRadius, final float endRadius) {
        versionCheck(new Runnable() {
            @Override
            public void run() {
                Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
                animator.setDuration(duration);
                view.setVisibility(View.VISIBLE);
                animator.start();
            }
        }, REVEAL_WARNING);
    }

    private static void conceal(final View view, final int duration, final int centerX, final int centerY, final float startRadius, final float endRadius) {
        versionCheck(new Runnable() {
            @Override
            public void run() {
                Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
                animator.setDuration(duration);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });
                animator.start();
            }
        }, REVEAL_WARNING);
    }

    public static void concealCenter(View view, int duration) {
        int centerX = view.getWidth() / 2;
        int centerY = view.getHeight() / 2;
        conceal(view, duration, centerX, centerY, getMaxRadius(view), 0);
    }

    public static void revealCenter(View view, int duration) {
        int centerX = view.getWidth() / 2;
        int centerY = view.getHeight() / 2;
        reveal(view, duration, centerX, centerY, 0, getMaxRadius(view));
    }

    public static void concealLeftTop(View view, int duration) {
        conceal(view, duration, 0, 0, getMaxRadius(view) * 2, 0);
    }

    public static void revealLeftTop(View view, int duration) {
        reveal(view, duration, 0, 0, 0, getMaxRadius(view) * 2);
    }

    public static void concealLeftBottom(View view, int duration) {
        conceal(view, duration, 0, view.getHeight(), getMaxRadius(view) * 2, 0);
    }

    public static void revealLeftBottom(View view, int duration) {
        reveal(view, duration, 0, view.getHeight(), 0, getMaxRadius(view) * 2);
    }

    public static void concealRightTop(View view, int duration) {
        conceal(view, duration, view.getWidth(), 0, getMaxRadius(view) * 2, 0);
    }

    public static void revealRightTop(View view, int duration) {
        reveal(view, duration, view.getWidth(), 0, 0, getMaxRadius(view) * 2);
    }

    public static void concealRightBottom(View view, int duration) {
        conceal(view, duration, view.getWidth(), view.getHeight(), getMaxRadius(view) * 2, 0);
    }

    public static void revealRightBottom(View view, int duration) {
        reveal(view, duration, view.getWidth(), view.getHeight(), 0, getMaxRadius(view) * 2);
    }

    private static float getMaxRadius(View view) {
        return Math.max(view.getHeight(), view.getWidth());
    }

    private static void versionCheck(Runnable runnable, String message) {
        if (takeEffect) {
            runnable.run();
        } else {
            jLogger.w(message);
        }
    }

    public static void revealActivity(final Activity activity, final int duration, final int centerX, final int centerY) {
        versionCheck(new Runnable() {
            @Override
            public void run() {
                final View rootView = activity.findViewById(android.R.id.content);
                rootView.setVisibility(View.INVISIBLE);
                rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        int newX = centerX - left;
                        int newY = centerY - top;
                        rootView.removeOnLayoutChangeListener(this);
                        reveal(rootView, duration, newX, newY, 0, getMaxRadius(rootView) * 2);
                    }
                });
            }
        }, REVEAL_WARNING);
    }

    public static void concealActivity(final Activity activity, final int duration, final int centerX, int centerY) {
        if (takeEffect) {
            final View rootView = activity.findViewById(android.R.id.content);
            Animator animator = ViewAnimationUtils.createCircularReveal(rootView, centerX, centerY, getMaxRadius(rootView) * 2, 0);
            animator.setDuration(duration);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootView.setVisibility(View.GONE);
                    activity.finish();
                }
            });
            animator.start();
        } else {
            activity.finish();
        }
    }

}
