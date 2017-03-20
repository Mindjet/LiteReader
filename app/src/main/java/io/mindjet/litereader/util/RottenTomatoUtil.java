package io.mindjet.litereader.util;

import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import io.mindjet.jetutil.logger.JLogger;
import io.mindjet.litereader.model.other.RottenTomatoes;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/17/17.
 */

public class RottenTomatoUtil {


    private static String baseUrl = "https://www.rottentomatoes.com/m/";
    private static JLogger jLogger = JLogger.get(RottenTomatoUtil.class.getSimpleName());

    public static void getRottenTomatoesData(final TextView tomatoMeter, final TextView audienceScore, final String name, final String year) {
        tomatoMeter.setText("获取数据中");
        audienceScore.setText("获取数据中");
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, RottenTomatoes>() {
                    @Override
                    public RottenTomatoes call(String s) {
                        return decodeData(preProcess(name));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RottenTomatoes>() {
                    @Override
                    public void call(RottenTomatoes data) {
                        if (data != null) {
                            tomatoMeter.setText(data.getTomatoMeter());
                            audienceScore.setText(data.getAudienceScore());
                        } else {
                            tryWithYear(tomatoMeter, audienceScore, name, year);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        jLogger.e("first try failed." + throwable);
                    }
                });
    }

    private static void tryWithYear(final TextView tomatoMeter, final TextView audienceScore, final String name, final String year) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, RottenTomatoes>() {
                    @Override
                    public RottenTomatoes call(String s) {
                        return decodeData(processWithYear(name, year));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RottenTomatoes>() {
                    @Override
                    public void call(RottenTomatoes data) {
                        if (data != null) {
                            tomatoMeter.setText(data.getTomatoMeter());
                            audienceScore.setText(data.getAudienceScore());
                        } else {
                            tomatoMeter.setText("暂无数据");
                            audienceScore.setText("暂无数据");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        tomatoMeter.setText("暂无数据");
                        audienceScore.setText("暂无数据");
                    }
                });
    }

    private static RottenTomatoes decodeData(final String name) {
        RottenTomatoes data = null;
        try {
            Document doc = Jsoup.connect(baseUrl + name).get();
            Element mainColumn = doc.getElementById("mainColumn");
            if (mainColumn.child(1).tagName().equals("h1")) {
                return null;
            }
            String tomatoData = doc.getElementsByClass("meter-value").first().child(0).text();
            String audienceData = doc.getElementsByClass("meter-value").get(2).child(0).text();
            data = new RottenTomatoes();
            data.setTomatoMeter(tomatoData);
            data.setAudienceScore(audienceData);
            jLogger.e(tomatoData + "  -- - - - - - -" + audienceData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static String preProcess(String name) {
        return name.replace(" ", "_");
    }

    private static String processWithYear(String name, String year) {
        return preProcess(name) + "_" + year;
    }

}