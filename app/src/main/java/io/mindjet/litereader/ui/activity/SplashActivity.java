package io.mindjet.litereader.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.concurrent.TimeUnit;

import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.file.SPUtil;
import io.mindjet.jetutil.task.Task;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ActivitySplashBinding;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.other.DailyWallpaper;
import io.mindjet.litereader.service.OtherService;
import io.mindjet.litereader.util.DateUtil;
import rx.functions.Action1;

/**
 * 每日壁纸启动页
 * <p>
 * Created by Mindjet on 3/15/17.
 */

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setContentView(mBinding.getRoot());
        mBinding.setData(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Courgette-Regular.ttf");
        mBinding.ivAppName.setTypeface(typeface);
        mBinding.ivAppName2.setTypeface(typeface);

        boolean showDailyWallpaper = SPUtil.getBoolean(this, Constant.KEY_SETTING_SHOW_WALLPAPER);
        boolean hasTimeLapsed = DateUtil.timeLapsed(SPUtil.getLong(this, Constant.KEY_APP_LAUNCH_MILLISECOND), 60);
        SPUtil.save(this, Constant.KEY_APP_LAUNCH_MILLISECOND, System.currentTimeMillis());
        if (!showDailyWallpaper && !hasTimeLapsed) {
            startActivity(MainActivity.intentFor(this));
            finish();
        } else {
            OtherService service = ServiceGen.create(OtherService.class);
            service.getDailyWallpaper()
                    .timeout(3000, TimeUnit.MILLISECONDS)
                    .compose(new ThreadDispatcher<DailyWallpaper>())
                    .subscribe(new Action1<DailyWallpaper>() {
                        @Override
                        public void call(final DailyWallpaper data) {
                            Glide.with(SplashActivity.this)
                                    .load(adjustResolution(data.data.get(0).url))
                                    .asBitmap()
                                    .placeholder(R.drawable.ic_placeholder)
                                    .thumbnail(0.1f)
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            mBinding.ivWallpaper.setImageBitmap(resource);
                                            mBinding.tvCopyright.setText(breakString(data.data.get(0).copyright));
                                            mBinding.flyWallpaper.setVisibility(View.VISIBLE);
                                        }
                                    });
                            next(3000);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            next(1000);
                        }
                    });
        }
    }

    private String adjustResolution(String url) {
        return "http://www.bing.com" + url.replace("1920x1080", "720x1280");
    }

    private String breakString(String string) {
        StringBuilder sb = new StringBuilder("");
        int index = string.indexOf('(');
        sb.append(string.substring(0, index).trim());
        sb.append("\n");
        sb.append(string.substring(index, string.length()));
        return sb.toString();
    }

    private void next(long time) {
        Task.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.intentFor(SplashActivity.this));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, time);
    }

}
