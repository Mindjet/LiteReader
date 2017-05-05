package io.mindjet.litereader.viewmodel;

/**
 * 收藏接口
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public interface ICollection {

    void initCollect();

    void updateCollectIcon(boolean isCollect);

    void manipulateCollect();

    void disCollect();

    void collect();
}
