package io.mindjet.litereader.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.concurrent.TimeUnit;

import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.file.SPUtil;
import io.mindjet.jetutil.task.Task;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.other.DailyWallpaper;
import io.mindjet.litereader.service.OtherService;
import io.mindjet.litereader.util.DateUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 每日壁纸启动页
 * <p>
 * Created by Jet on 3/15/17.
 */

public class SplashActivity extends AppCompatActivity {

    private LinearLayout wrapper;
    private TextView copyright;
    private ImageView wallpaper;
    private ImageView gradient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean showDailyWallpaper = SPUtil.getBoolean(this, Constant.KEY_SETTING_SHOW_WALLPAPER);
        boolean hasTimeLapsed = DateUtil.timeLapsed(SPUtil.getLong(this, Constant.KEY_APP_LAUNCH_MILLISECOND), 60);
        SPUtil.save(this, Constant.KEY_APP_LAUNCH_MILLISECOND, System.currentTimeMillis());
        if (!showDailyWallpaper && !hasTimeLapsed) {
            startActivity(MainActivity.intentFor(this));
        } else {
            wrapper = (LinearLayout) findViewById(R.id.lly_wrapper);
            copyright = (TextView) findViewById(R.id.tv_copyright);
            wallpaper = (ImageView) findViewById(R.id.iv_wallpaper);
            gradient = (ImageView) findViewById(R.id.iv_gradient);

            OtherService service = ServiceGen.create(OtherService.class);
            service.getDailyWallpaper()
                    .timeout(4000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<DailyWallpaper>() {
                        @Override
                        public void call(DailyWallpaper data) {
                            gradient.setVisibility(View.VISIBLE);
                            wrapper.setVisibility(View.VISIBLE);
                            copyright.setVisibility(View.VISIBLE);
                            Glide.with(SplashActivity.this)
                                    .load(adjustResolution(data.data.get(0).url))
                                    .placeholder(R.drawable.ic_placeholder)
                                    .thumbnail(0.1f)
                                    .into(wallpaper);
                            copyright.setText(breakString(data.data.get(0).copyright));
                            next();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            wallpaper.setImageResource(R.drawable.ic_placeholder);
                            next();
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

    private void next() {
        Task.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.intentFor(SplashActivity.this));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 4000);
    }

}
