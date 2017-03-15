package io.mindjet.litereader.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetimage.loader.ImageLoader;
import io.mindjet.jetutil.task.Task;
import io.mindjet.litereader.R;
import io.mindjet.litereader.model.other.DailyWallpaper;
import io.mindjet.litereader.service.OtherService;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
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

        wrapper = (LinearLayout) findViewById(R.id.lly_wrapper);
        copyright = (TextView) findViewById(R.id.tv_copyright);
        wallpaper = (ImageView) findViewById(R.id.iv_wallpaper);
        gradient = (ImageView) findViewById(R.id.iv_gradient);

        OtherService service = ServiceGen.create(OtherService.class);
        service.getWallpaper()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyWallpaper>() {
                    @Override
                    public void call(DailyWallpaper data) {
                        gradient.setVisibility(View.VISIBLE);
                        wrapper.setVisibility(View.VISIBLE);
                        copyright.setVisibility(View.VISIBLE);
                        ImageLoader.load(wallpaper, adjustResolution(data.data.get(0).url));
                        copyright.setText(data.data.get(0).copyright);
                        next();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //TODO 在这里设置默认欢迎界面
                        wallpaper.setImageResource(R.drawable.ic_placeholder);
                        next();
                    }
                });
    }

    private String adjustResolution(String url) {
        return "http://www.bing.com" + url.replace("1920x1080", getResources().getDisplayMetrics().widthPixels + "x" + getResources().getDisplayMetrics().heightPixels);
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
