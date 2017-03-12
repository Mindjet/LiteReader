package io.mindjet.jetgear.mvvm.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.mindjet.jetutil.logger.JLogger;
import io.mindjet.jetutil.manager.ActivityManager;

/**
 * Created by Jet on 2/15/17.
 */

public abstract class BaseActivity extends Activity {

    public JLogger jLogger = JLogger.get(getClass().getSimpleName());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.finishActivity(this);
    }
}
