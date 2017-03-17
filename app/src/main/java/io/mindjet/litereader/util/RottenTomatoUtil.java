package io.mindjet.litereader.util;

import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.logger.JLogger;
import io.mindjet.litereader.model.other.RottenTomatoes;
import io.mindjet.litereader.service.OtherService;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/17/17.
 */

public class RottenTomatoUtil {

    private static OtherService service = ServiceGen.create(OtherService.class);
    private static JLogger jLogger = JLogger.get(RottenTomatoUtil.class.getSimpleName());

    public static void getRottenTomatoesData(final TextView tomatoMeter, final TextView audienceScore, final String name, final String year) {
        String formatName = preProcess(name);
        service.getRottenTomatoesData(formatName)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, RottenTomatoes>() {
                    @Override
                    public RottenTomatoes call(String html) {
                        return decodeHtml(html);
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
                            jLogger.e("Retry with year");
                            tryWithYear(tomatoMeter, audienceScore, name, year);        //加上年份后缀再次尝试
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        jLogger.e(throwable.toString());
                        tryWithYear(tomatoMeter, audienceScore, name, year);
                    }
                });
    }

    private static void tryWithYear(final TextView tomatoMeter, final TextView audienceScore, String name, String year) {
        String formatName = processWithYear(name, year);
        service.getRottenTomatoesData(formatName)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, RottenTomatoes>() {
                    @Override
                    public RottenTomatoes call(String html) {
                        return decodeHtml(html);
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
                        jLogger.e(throwable.toString());
                    }
                });
    }

    private static RottenTomatoes decodeHtml(String html) {
        RottenTomatoes data = new RottenTomatoes();
        Document doc = Jsoup.parse(html);
        Element mainColumn = doc.getElementById("mainColumn");
        if (mainColumn.child(1).tagName().equals("h1")) {
            return null;
        }
        String tomatoMeter = doc.getElementsByClass("meter-value").first().child(0).text();
        String audienceScore = doc.getElementsByClass("meter-value").get(2).child(0).text();
        data.setTomatoMeter(tomatoMeter);
        data.setAudienceScore(audienceScore);
        jLogger.e("asdassadsa" + tomatoMeter + "asdasdsadsasda" + audienceScore);
        return data;
    }

    private static String preProcess(String name) {
        return name.replace(" ", "_");
    }

    private static String processWithYear(String name, String year) {
        return preProcess(name) + "_" + year;
    }

}