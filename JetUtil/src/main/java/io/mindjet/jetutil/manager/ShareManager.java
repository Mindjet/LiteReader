package io.mindjet.jetutil.manager;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetutil.hint.Toaster;

/**
 * Share manager, handles shares to QQ, Qzone, Wechat and Weibo.
 * <p>
 * Created by Jet on 4/19/17.
 */

public class ShareManager {

    private static ShareManager sInstance;

    private final String PACKAGE_NAME_QQ = "com.tencent.mobileqq";
    private final String PACKAGE_NAME_WECHAT = "com.tencent.mm";
    private final String PACKAGE_NAME_QZONE = "com.qzone";
    private final String PACKAGE_NAME_WEIBO = "com.sina.weibo";

    private Context context;

    private ShareManager(Context context) {
        this.context = context;
    }

    public static ShareManager with(Context context) {
        if (sInstance == null) {
            synchronized (ShareManager.class) {
                if (sInstance == null) {
                    sInstance = new ShareManager(context);
                    return sInstance;
                }
            }
        }
        return sInstance;
    }

    /**
     * Share to QQ with opening a choose.
     *
     * @param text the content to share.
     */
    public void shareQQ(String text) {
        Intent intent = createTextIntent(text);
        intent.setClassName(PACKAGE_NAME_QQ, "com.tencent.mobileqq.activity.JumpActivity");
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toaster.toast(context, "尚未安装QQ客户端，或客户端版本太低");
        }
    }

    /**
     * Share to Wechat with opening a choose.
     *
     * @param text the content to share.
     */
    public void shareWechat(String text) {
        Intent intent = createTextIntent(text);
        intent.setClassName(PACKAGE_NAME_WECHAT, "com.tencent.mm.ui.tools.ShareImgUI");
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toaster.toast(context, "尚未安装微信客户端，或客户端版本太低");
        }
    }

    /**
     * Share to Qzone.
     *
     * @param text the content to share.
     */
    public void shareQzone(String text) {
        Intent intent = createTextIntent(text);
        intent.setPackage(PACKAGE_NAME_QZONE);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toaster.toast(context, "尚未安装QQ空间客户端，或客户端版本太低");
        }
    }

    /**
     * Share to Weibo.
     *
     * @param text the content to share.
     */
    public void shareWeibo(String text) {
        Intent intent = createTextIntent(text);
        intent.setPackage(PACKAGE_NAME_WEIBO);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toaster.toast(context, "尚未安装微博客户端，或客户端版本太低");
        }
    }

    /**
     * Invoke system share. The system will choose apps that can share the text
     * and show a dialog to let users make their choice.
     *
     * @param text the content to share.
     */
    public void shareAll(String text) {
        Intent intent = createTextIntent(text);
        context.startActivity(intent);
    }

    /**
     * Create an intent with an extra of {@link Intent#EXTRA_TEXT} and an action of {@link Intent#ACTION_SEND}.
     *
     * @param text the text the Intent carries.
     * @return an intent carrying the text with a SEND action.
     */
    private Intent createTextIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return intent;
    }


}
