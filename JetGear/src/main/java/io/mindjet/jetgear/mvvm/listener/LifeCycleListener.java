package io.mindjet.jetgear.mvvm.listener;

import android.view.Menu;

/**
 * Created by Jet on 2/22/17.
 */

public interface LifeCycleListener {

    void onDestroy();

    void onStop();

    void onResume();

    /**
     * @return Whether the event is consumed or not.
     * <p>
     * If the event has been consumed, please return true, so that the default callback will not be invoked.
     */
    boolean onBackPressed();

    boolean onCreateOptionMenu(Menu menu);

}
