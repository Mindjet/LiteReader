package io.mindjet.jetwidget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Jet on 3/6/17.
 */

public class JToolBar extends Toolbar {

    private boolean layoutReady;

    public JToolBar(Context context) {
        this(context, null);
    }

    public JToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (!layoutReady) {
            //if version is later than KitKat, an artificial status bar are supposed to added.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ViewGroup.LayoutParams lp = getLayoutParams();
                lp.height = getHeight() + getStatusBarHeight();
                int paddingLeft = getPaddingLeft();
                setPadding(paddingLeft, getStatusBarHeight(), 0, 0);
            }
            layoutReady = true;
        }
    }

    /**
     * Note that to use this method rather than {@link Toolbar#setNavigationIcon(int)}, as this method will expand the click area.
     *
     * @param icon the navigation icon.
     */
    public void setNavIcon(@DrawableRes int icon) {
        setNavigationIcon(icon);
        AppCompatImageButton navIcon = (AppCompatImageButton) getChildAt(1);
        Toolbar.LayoutParams lp = (LayoutParams) navIcon.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        navIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        navIcon.setLayoutParams(lp);
    }

    private int getStatusBarHeight() {
        return getResources().getDimensionPixelSize(R.dimen.tool_bar_status_bar_height);
    }

}
