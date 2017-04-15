package io.mindjet.litereader.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.jetutil.file.SPUtil;
import io.mindjet.jetutil.logger.JLogger;
import io.mindjet.litereader.entity.ChannelCode;
import io.mindjet.litereader.entity.Constant;

/**
 * 频道工具类
 * <p>
 * Created by Mindjet on 2017/4/14.
 */

public class ChannelUtil {

    private final static JLogger jLogger = JLogger.get(ChannelUtil.class.getSimpleName());
    private final static String KEY_SUBSCRIBED_CHANNELS = "key_subscribed_channels";

    private final static String separator = ",";
    private final static List<String> allChannels = new ArrayList<>();
    private final static List<String> defaultChannels = new ArrayList<>();

    //TODO 注意后期更新频道数目
    static {
        allChannels.add(ChannelCode.ZHIHU);
        allChannels.add(ChannelCode.DAILY);
        allChannels.add(ChannelCode.DOUBAN);

        defaultChannels.add(ChannelCode.ZHIHU);
        defaultChannels.add(ChannelCode.DAILY);
        defaultChannels.add(ChannelCode.DOUBAN);
    }

    public static List<String> getAllChannels() {
        return allChannels;
    }

    public static List<String> getDefaultChannels() {
        return defaultChannels;
    }

    /**
     * 获取除默认频道 list 之外的所有频道
     *
     * @return 除默认频道 list 之外的所有频道
     */
    private static List<String> excludeDefaultChannels() {
        List<String> result = new ArrayList<>();
        result.addAll(allChannels);
        result.removeAll(defaultChannels);
        return result;
    }

    /**
     * 在第一个 list 删除所有 第二个 list 的元素
     *
     * @param parent 被删除的 list
     * @param child  匹配的 list
     * @return 删除后的 list
     */
    private static List<String> excludeChannels(List<String> parent, List<String> child) {
        List<String> result = new ArrayList<>();
        result.addAll(parent);
        result.removeAll(child);
        return result;
    }

    /**
     * 判断两个List是否完全相同（元素个数，数值，顺序）
     *
     * @param list1 目标list
     * @param list2 目标list
     * @return list是否完全相同
     */
    public static boolean isSame(List<String> list1, List<String> list2) {
        return Arrays.equals(list1.toArray(), list2.toArray());
    }

    /**
     * 判断目标list是否与默认list完全相同
     *
     * @param list 目标list
     * @return list是否相同
     */
    public static boolean isSameAsDefault(List<String> list) {
        return isSame(list, defaultChannels);
    }

    /**
     * 获得储存在 SharedPreferences 的频道列表，若无储存则返回默认列表
     *
     * @return 储存在 SharedPreferences 的频道列表
     */
    public static List<String> getChannels(Context context) {
        String channelsString = SPUtil.getString(context, KEY_SUBSCRIBED_CHANNELS);
        if (channelsString == null)
            return defaultChannels;
        return Arrays.asList(channelsString.split(separator));
    }

    /**
     * 将频道列表储存在 SharedPreferences 中
     * <p>
     * 由于 SharedPreferences 对于字符串只能储存 String 或者 Set&lt;String&gt; 中，后者没有顺序的特性，故要将 List 用分隔符拼接成一个 String
     */
    public static void saveChannels(Context context, List<String> channels) {
        StringBuilder builder = new StringBuilder();
        for (String channel : channels) {
            builder.append(channel)
                    .append(separator);
        }
        String channelsString = builder.substring(0, builder.length() - 1);
        SPUtil.save(context, KEY_SUBSCRIBED_CHANNELS, channelsString);
        jLogger.e("Channels saved: " + channelsString);
    }

    /**
     * 获得排列好的频道（即选中的排在前面，未选中的排在后面）
     *
     * @return 根据选中状态排列好的频道
     */
    public static List<String> getSortedChannels(Context context) {
        List<String> subscribedChannels = getChannels(context);
        List<String> sortedChannels = new ArrayList<>();
        sortedChannels.addAll(subscribedChannels);
        sortedChannels.addAll(excludeChannels(allChannels, subscribedChannels));
        return sortedChannels;
    }

}
